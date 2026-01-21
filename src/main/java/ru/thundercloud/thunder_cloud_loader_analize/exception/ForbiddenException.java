/*
 * VTB Group. Do not reproduce without permission in writing.
 *
 * Copyright (c) 2026 VTB Group. All rights reserved.
 */

package ru.thundercloud.thunder_cloud_loader_analize.exception;

import lombok.Getter;
import ru.thundercloud.thunder_cloud_loader_analize.exception.dto.HandleError;

/**
 *
 * @author DRakovskiy
 */

@Getter
public class ForbiddenException extends BaseException {

    private HandleError handleException;

    public ForbiddenException(String message, HandleError handleException) {
        super(message);
        this.handleException = handleException;
    }

    public ForbiddenException(String message) {
        super(message);
    }


}
