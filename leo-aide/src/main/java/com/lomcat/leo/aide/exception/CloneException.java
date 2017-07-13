/* 
 * Copyright Lomcat and/or its affiliates.
 * 
 * See the NOTICE file distributed with this work for additional information regarding copyright ownership.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, 
 * software distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License.
 */
package com.lomcat.leo.aide.exception;

/**
 * 克隆异常
 *
 * @author Kuniel - kuniel@lomcat.com
 * @version 2017-04-26 20:46
 * @since 1.0.0
 */
public class CloneException extends Exception {
    private static final long serialVersionUID = -1604814898529431366L;

    public CloneException() {
        super();
    }

    public CloneException(String message) {
        super(message);
    }

    public CloneException(String message, Throwable cause) {
        super(message, cause);
    }

    public CloneException(Throwable cause) {
        super(cause);
    }

    protected CloneException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
