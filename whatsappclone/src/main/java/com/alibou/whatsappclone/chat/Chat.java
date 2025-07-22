package com.alibou.whatsappclone.chat;

import com.alibou.whatsappclone.common.BaseAuditingEntity;
import com.alibou.whatsappclone.message.Message;
import com.alibou.whatsappclone.message.MessageState;
import com.alibou.whatsappclone.message.MessageType;
import com.alibou.whatsappclone.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "chat")
@NamedQuery(name = ChatConstants.FIND_CHAT_BY_SENDER_ID,
    query = "SELECT DISTINCT c FROM Chat c WHERE c.sender.id = :senderId OR c.receiver.id = :senderId ORDER BY c.createdDate DESC")
@NamedQuery(name = ChatConstants.FIND_CHAT_BY_SENDER_ID_AND_RECEIVER_ID,
    query = "SELECT DISTINCT c FROM Chat c WHERE (c.sender.id = :senderId AND c.receiver.id = :receiverId) OR (c.sender.id = :receiverId AND c.receiver.id = :senderId)")
public class Chat extends BaseAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;
    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;
    @OneToMany(mappedBy = "chat", fetch = FetchType.EAGER)
    @OrderBy("createdDate DESC")
    private List<Message> messages;

    @Transient
    public String getChatName(final String senderId){
        if(receiver.getId().equals(senderId)){
            return sender.getFirstName() + " " + sender.getLastName();
        }
        return receiver.getFirstName() + " " + receiver.getLastName();
    }
    @Transient
    public long getUnreadMessages(final String senderId){
        return messages
                .stream()
                .filter(m -> m.getReceiverId().equals(senderId))
                .filter(m -> MessageState.SENT == m.getState())
                .count();
    }

    @Transient
    public String getLastMessage(){
        if (messages != null && !messages.isEmpty()){
            if(messages.get(0).getType() != MessageType.TEXT){
                return "Attachment";
            }
            return messages.get(0).getContent();
        }
        return null;
    }
    @Transient
    public String getLastMessageTime(){
        if (messages != null && !messages.isEmpty()){
            return messages.get(0).getCreatedDate().toString();
        }
        return null;
    }
}
