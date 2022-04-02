package es.vytale.milanesa.common.friends.communication.reply;

import es.vytale.milanesa.common.friends.communication.action.FriendReplyAction;
import lombok.Data;

import java.util.UUID;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
@Data
public class FriendReply {
    private final UUID holderUuid;
    private final UUID otherUuid;
    private final String holderName;
    private final String otherName;
    private final FriendReplyAction friendReplyAction;
}