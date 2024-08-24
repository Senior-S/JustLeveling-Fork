package com.seniors.justlevelingfork.client.core;

import com.seniors.justlevelingfork.registry.title.Title;

import java.util.LinkedList;

public class TitleQueue {
    LinkedList<Title> list = new LinkedList<>();

    public void enqueue(Title title) {
        this.list.addLast(title);
    }

    public void dequeue() {
        this.list.removeFirst();
    }

    public Title peek() {
        return this.list.getFirst().get();
    }

    public int count() {
        return this.list.size();
    }

    public void setList(LinkedList<Title> newList) {
        this.list = newList;
    }
}


