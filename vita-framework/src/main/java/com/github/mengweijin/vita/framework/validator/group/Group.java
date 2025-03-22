package com.github.mengweijin.vita.framework.validator.group;

/**
 * @author mengweijin
 */
public interface Group {

    interface Default extends jakarta.validation.groups.Default {
    }

    interface Select {
    }

    interface Create {
    }

    interface Update {
    }

    interface Delete {
    }
}
