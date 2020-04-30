package com.tonikolaba.springvuejsXstarter.model;

import javax.persistence.*;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;

/**
 * Created by NdueKolaba on 13-Jan-19.
 */

@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    @Column(name = "active")
    private boolean active;

    @Column(name = "albums")
    @ElementCollection
    @CollectionTable(name = "albums", joinColumns = @JoinColumn(name = "id"))
    private Set<UUID> albums;

    public Customer() {
    }

    public Customer(String name, int age) {
        this.name = name;
        this.age = age;
        this.active = false;
    }

    public long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return this.age;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<UUID> getAlbums() {
        return albums;
    }

    public void addAlbum(UUID album) {
        if (this.albums == null) {
            this.albums = Collections.emptySet();
        }
        this.albums.add(album);
    }

    public void removeAlbum(UUID album) {
        if (this.albums == null) {
            return;
        }
        this.albums.remove(album);
    }

    @Override
    public String toString() {
        return "Customer [id=" + id + ", name=" + name + ", age=" + age + ", active=" + active + "]";
    }


}
