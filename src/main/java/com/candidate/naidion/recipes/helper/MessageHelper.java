package com.candidate.naidion.recipes.helper;

import com.candidate.naidion.recipes.enums.ErrorsEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;

import javax.annotation.PostConstruct;
import java.util.Locale;

@RequiredArgsConstructor
public final class MessageHelper {

    private final MessageSource messageSource;
    private static MessageSourceAccessor accessor;

    @PostConstruct
    public void init() {
        accessor = new MessageSourceAccessor(messageSource, Locale.getDefault());
    }

    public static String get(ErrorsEnum code, Object... args) {
        return accessor.getMessage(String.valueOf(code.getCode()), args);
    }
}