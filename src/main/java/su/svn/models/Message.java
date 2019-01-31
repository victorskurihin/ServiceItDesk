/*
 * Message.java
 * This file was last modified at 2019.01.24 23:59 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package su.svn.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static su.svn.models.Message.FIND_ALL;
import static su.svn.models.Message.FIND_ALL_WHERE_TEXT;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "pm_message")
@NamedQueries({
    @NamedQuery(
        name = FIND_ALL,
        query = "SELECT m FROM Message m"
    ),
    @NamedQuery(
        name = FIND_ALL_WHERE_TEXT,
        query = "SELECT m FROM Message m WHERE m.text LIKE :text"
    ),
})
public class Message implements DataSet
{
    public static final String FIND_ALL = "Message.findAll";
    public static final String FIND_ALL_WHERE_TEXT = "Message.findAllWhereName";

    @Id
    @SequenceGenerator(name = "message_identifier", sequenceName = "message_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "message_identifier")
    @Column(name = "message_id", nullable = false, unique = true)
    private Long id = 0L;

    @Basic
    @Column(name = "message_text", nullable = false)
    private String text;
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
