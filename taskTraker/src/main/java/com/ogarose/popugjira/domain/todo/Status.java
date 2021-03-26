package com.ogarose.popugjira.domain.todo;

public enum Status {
    OPEN {
        @Override
        public boolean canBeChangeTo(Status status) {
            return true;
        }
    },
    CLOSED {
        @Override
        public boolean canBeChangeTo(Status status) {
            return status.equals(Status.OPEN);
        }
    };

    public abstract boolean canBeChangeTo(Status status);
}
