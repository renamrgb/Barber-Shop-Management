package com.rpx.bsm.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "message_template")
@Getter
@Setter
@NoArgsConstructor
public class MessageTemplate implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Auto incremento
    private Long Id;
    private String title;
    private String body;
    private Boolean isActive;

    public MessageTemplate(String title, String body, Boolean isActive) {
        this.title = title;
        this.body = body;
        this.isActive = isActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageTemplate that = (MessageTemplate) o;
        return Objects.equals(Id, that.Id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id);
    }

    @Override
    public String toString() {
        return "ModeloMensagem{" +
                "Id=" + Id +
                ", titulo='" + title + '\'' +
                ", texto='" + body + '\'' +
                ", ativo=" + isActive +
                '}';
    }
}
