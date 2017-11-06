package org.cr.rheos.ui.events;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public class RowSelected<E> implements Event {
    private final E selection;
}
