package com.devahmed.demo.onlinepharmacy.Common.MVC;

public interface ObservableViewMvc<ListenerType> extends MvcView {

    void registerListener(ListenerType listenerType);

    void unregisterListener(ListenerType listenerType);


}
