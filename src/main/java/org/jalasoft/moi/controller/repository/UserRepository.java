/*
 * Copyright (c) 2020 Jalasoft.
 *
 * This software is the confidential and proprietary information of Jalasoft.
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Jalasoft.
 */

package org.jalasoft.moi.controller.repository;

import org.jalasoft.moi.domain.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Defines the user CRUD repository.
 *
 * @author Carlos Meneses
 * @version 1.2
 */
public interface UserRepository extends CrudRepository<User, Long> {
}
