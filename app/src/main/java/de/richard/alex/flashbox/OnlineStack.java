package de.richard.alex.flashbox;

import java.util.List;

public class OnlineStack {
        String _id;
        String name;
        String owner;
        int rating;
        List<String> tags;
        List<Card> content;
        String info;

        public OnlineStack(String _id, String name, String owner, int rating, List<String> tags, List<Card> content, String info) {
            this._id = _id;
            this.name = name;
            this.owner = owner;
            this.rating = rating;
            this.tags = tags;
            this.content = content;
            this.info = info;
        }

    /*
    converts from OnlineStack to CardStack
     */

    public CardStack toCardStack() {
        CardStack cardstack = new CardStack(this.owner,this.name,this.info,this.getTags());
        List<Card> cards = this.content;
        while (!(cards.isEmpty())) {
            cardstack.addCard(cards.get(0));
            cards.remove(0);
        }
        return cardstack;
    }


    /*
    returns Tags as one String
     */

    private String getTags() {
        List<String> taglist = tags;
        String resinfo = "";
        while (!(taglist.isEmpty())) {
            resinfo = resinfo + taglist.get(0) + " ";
            taglist.remove(0);
        }
        return resinfo;
    }

}
