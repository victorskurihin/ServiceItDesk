/*
 * UserDao.java
 * This file was last modified at 2019.01.25 20:05 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package su.svn.db;

import su.svn.models.User;

import java.util.List;
import java.util.Optional;

public interface UserDao extends Dao<User, Long>
{
    Optional<User> findByIdWithDetails(Long id);

    List<User> findByName(String value);

    List<User> findByDescription(String value);
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
