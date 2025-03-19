package com.sgupta.core.delegator

interface DelegateAdapterItem {
    fun id(): Any

    fun content(): Any

    fun payload(other: Any): Payloadable = Payloadable.None

    /**
     * Simple marker interface for payloads
     */
    interface Payloadable {
        object None : Payloadable
    }
}