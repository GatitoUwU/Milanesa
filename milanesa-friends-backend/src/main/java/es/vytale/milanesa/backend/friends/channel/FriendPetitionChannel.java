package es.vytale.milanesa.backend.friends.channel;

import es.vytale.milanesa.backend.friends.FriendsBackend;
import es.vytale.milanesa.backend.friends.petition.FriendUser;
import es.vytale.milanesa.common.friends.communication.FriendPetitionAction;
import es.vytale.milanesa.common.friends.communication.FriendPetitionRequest;
import es.vytale.milanesa.common.redis.data.MilanesaChannel;
import es.vytale.milanesa.common.redis.data.MilanesaMessage;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
public class FriendPetitionChannel extends MilanesaChannel {

    private final FriendsBackend friendsBackend;

    public FriendPetitionChannel(FriendsBackend friendsBackend) {
        super("friends::petition");
        this.friendsBackend = friendsBackend;
    }

    @Override
    public void handle(MilanesaMessage milanesaMessage) {
        FriendPetitionRequest fpr = FriendsBackend.getGson().fromJson(milanesaMessage.getData(), FriendPetitionRequest.class);
        if (fpr.getFriendPetitionAction().equals(FriendPetitionAction.ASK)) {
            FriendUser friendUser = friendsBackend.getFriendUserManager().getOrCreate(fpr.getOther());
            //if (friendUser.hasPetition(fpr.getHolder())) {}
        }
    }
}
