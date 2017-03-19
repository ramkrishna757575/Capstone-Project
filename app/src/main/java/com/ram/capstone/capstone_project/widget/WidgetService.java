package com.ram.capstone.capstone_project.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by ramkrishna on 18/03/17
 */

public class WidgetService extends RemoteViewsService {
  @Override
  public RemoteViewsFactory onGetViewFactory(Intent intent) {
    return (new ListProvider(this.getApplicationContext(), intent));
  }
}
