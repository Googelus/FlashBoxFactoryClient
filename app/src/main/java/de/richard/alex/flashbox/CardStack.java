package de.richard.alex.flashbox;

import java.util.LinkedList;
import java.util.List;

public class CardStack {

    List<Card> cards;
    String author;
    String name;
    String info;
    String tags;


    public CardStack(String author, String name) {
        this.author = author;
        this.name = name;
        this.cards = new LinkedList<Card>();
        this.tags = "";
        this.info = "";

    }

    public CardStack(String author, String name, String info, String tags) {
        this.author = author;
        this.name = name;
        this.cards = new LinkedList<Card>();
        this.tags = tags;
        this.info = info;

    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public Card getCard(int number) {
        if (number > cards.size()-1)
            return null;
        return cards.get(number);
    }

    public void removeCard(int number) {
        cards.remove(number);
    }

    public void removeCard(Card card) {
        cards.remove(card);
    }

    public int getSize() {
        return cards.size();
    }

    public String getAuthor() {
        return author;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getName() {
        return name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Card> getCards() {
        return cards;
    }
}
