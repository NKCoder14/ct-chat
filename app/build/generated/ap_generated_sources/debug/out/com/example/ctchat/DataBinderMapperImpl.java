package com.example.ctchat;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.example.ctchat.databinding.ActivityChatBindingImpl;
import com.example.ctchat.databinding.ActivityCreateGroupBindingImpl;
import com.example.ctchat.databinding.ActivityGroupChatBindingImpl;
import com.example.ctchat.databinding.ActivityLoginBindingImpl;
import com.example.ctchat.databinding.ActivityMainBindingImpl;
import com.example.ctchat.databinding.ActivityRegisterBindingImpl;
import com.example.ctchat.databinding.FragmentCallsBindingImpl;
import com.example.ctchat.databinding.FragmentChatsBindingImpl;
import com.example.ctchat.databinding.FragmentStatusBindingImpl;
import com.example.ctchat.databinding.ItemGroupBindingImpl;
import com.example.ctchat.databinding.ItemReceivedMessageBindingImpl;
import com.example.ctchat.databinding.ItemSentMessageBindingImpl;
import com.example.ctchat.databinding.ItemUserBindingImpl;
import com.example.ctchat.databinding.ItemUserSelectableBindingImpl;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.RuntimeException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBinderMapperImpl extends DataBinderMapper {
  private static final int LAYOUT_ACTIVITYCHAT = 1;

  private static final int LAYOUT_ACTIVITYCREATEGROUP = 2;

  private static final int LAYOUT_ACTIVITYGROUPCHAT = 3;

  private static final int LAYOUT_ACTIVITYLOGIN = 4;

  private static final int LAYOUT_ACTIVITYMAIN = 5;

  private static final int LAYOUT_ACTIVITYREGISTER = 6;

  private static final int LAYOUT_FRAGMENTCALLS = 7;

  private static final int LAYOUT_FRAGMENTCHATS = 8;

  private static final int LAYOUT_FRAGMENTSTATUS = 9;

  private static final int LAYOUT_ITEMGROUP = 10;

  private static final int LAYOUT_ITEMRECEIVEDMESSAGE = 11;

  private static final int LAYOUT_ITEMSENTMESSAGE = 12;

  private static final int LAYOUT_ITEMUSER = 13;

  private static final int LAYOUT_ITEMUSERSELECTABLE = 14;

  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(14);

  static {
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.ctchat.R.layout.activity_chat, LAYOUT_ACTIVITYCHAT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.ctchat.R.layout.activity_create_group, LAYOUT_ACTIVITYCREATEGROUP);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.ctchat.R.layout.activity_group_chat, LAYOUT_ACTIVITYGROUPCHAT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.ctchat.R.layout.activity_login, LAYOUT_ACTIVITYLOGIN);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.ctchat.R.layout.activity_main, LAYOUT_ACTIVITYMAIN);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.ctchat.R.layout.activity_register, LAYOUT_ACTIVITYREGISTER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.ctchat.R.layout.fragment_calls, LAYOUT_FRAGMENTCALLS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.ctchat.R.layout.fragment_chats, LAYOUT_FRAGMENTCHATS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.ctchat.R.layout.fragment_status, LAYOUT_FRAGMENTSTATUS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.ctchat.R.layout.item_group, LAYOUT_ITEMGROUP);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.ctchat.R.layout.item_received_message, LAYOUT_ITEMRECEIVEDMESSAGE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.ctchat.R.layout.item_sent_message, LAYOUT_ITEMSENTMESSAGE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.ctchat.R.layout.item_user, LAYOUT_ITEMUSER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.ctchat.R.layout.item_user_selectable, LAYOUT_ITEMUSERSELECTABLE);
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = view.getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
        case  LAYOUT_ACTIVITYCHAT: {
          if ("layout/activity_chat_0".equals(tag)) {
            return new ActivityChatBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_chat is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYCREATEGROUP: {
          if ("layout/activity_create_group_0".equals(tag)) {
            return new ActivityCreateGroupBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_create_group is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYGROUPCHAT: {
          if ("layout/activity_group_chat_0".equals(tag)) {
            return new ActivityGroupChatBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_group_chat is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYLOGIN: {
          if ("layout/activity_login_0".equals(tag)) {
            return new ActivityLoginBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_login is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYMAIN: {
          if ("layout/activity_main_0".equals(tag)) {
            return new ActivityMainBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_main is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYREGISTER: {
          if ("layout/activity_register_0".equals(tag)) {
            return new ActivityRegisterBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_register is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTCALLS: {
          if ("layout/fragment_calls_0".equals(tag)) {
            return new FragmentCallsBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_calls is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTCHATS: {
          if ("layout/fragment_chats_0".equals(tag)) {
            return new FragmentChatsBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_chats is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTSTATUS: {
          if ("layout/fragment_status_0".equals(tag)) {
            return new FragmentStatusBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_status is invalid. Received: " + tag);
        }
        case  LAYOUT_ITEMGROUP: {
          if ("layout/item_group_0".equals(tag)) {
            return new ItemGroupBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for item_group is invalid. Received: " + tag);
        }
        case  LAYOUT_ITEMRECEIVEDMESSAGE: {
          if ("layout/item_received_message_0".equals(tag)) {
            return new ItemReceivedMessageBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for item_received_message is invalid. Received: " + tag);
        }
        case  LAYOUT_ITEMSENTMESSAGE: {
          if ("layout/item_sent_message_0".equals(tag)) {
            return new ItemSentMessageBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for item_sent_message is invalid. Received: " + tag);
        }
        case  LAYOUT_ITEMUSER: {
          if ("layout/item_user_0".equals(tag)) {
            return new ItemUserBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for item_user is invalid. Received: " + tag);
        }
        case  LAYOUT_ITEMUSERSELECTABLE: {
          if ("layout/item_user_selectable_0".equals(tag)) {
            return new ItemUserSelectableBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for item_user_selectable is invalid. Received: " + tag);
        }
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
    if(views == null || views.length == 0) {
      return null;
    }
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = views[0].getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
      }
    }
    return null;
  }

  @Override
  public int getLayoutId(String tag) {
    if (tag == null) {
      return 0;
    }
    Integer tmpVal = InnerLayoutIdLookup.sKeys.get(tag);
    return tmpVal == null ? 0 : tmpVal;
  }

  @Override
  public String convertBrIdToString(int localId) {
    String tmpVal = InnerBrLookup.sKeys.get(localId);
    return tmpVal;
  }

  @Override
  public List<DataBinderMapper> collectDependencies() {
    ArrayList<DataBinderMapper> result = new ArrayList<DataBinderMapper>(1);
    result.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
    return result;
  }

  private static class InnerBrLookup {
    static final SparseArray<String> sKeys = new SparseArray<String>(1);

    static {
      sKeys.put(0, "_all");
    }
  }

  private static class InnerLayoutIdLookup {
    static final HashMap<String, Integer> sKeys = new HashMap<String, Integer>(14);

    static {
      sKeys.put("layout/activity_chat_0", com.example.ctchat.R.layout.activity_chat);
      sKeys.put("layout/activity_create_group_0", com.example.ctchat.R.layout.activity_create_group);
      sKeys.put("layout/activity_group_chat_0", com.example.ctchat.R.layout.activity_group_chat);
      sKeys.put("layout/activity_login_0", com.example.ctchat.R.layout.activity_login);
      sKeys.put("layout/activity_main_0", com.example.ctchat.R.layout.activity_main);
      sKeys.put("layout/activity_register_0", com.example.ctchat.R.layout.activity_register);
      sKeys.put("layout/fragment_calls_0", com.example.ctchat.R.layout.fragment_calls);
      sKeys.put("layout/fragment_chats_0", com.example.ctchat.R.layout.fragment_chats);
      sKeys.put("layout/fragment_status_0", com.example.ctchat.R.layout.fragment_status);
      sKeys.put("layout/item_group_0", com.example.ctchat.R.layout.item_group);
      sKeys.put("layout/item_received_message_0", com.example.ctchat.R.layout.item_received_message);
      sKeys.put("layout/item_sent_message_0", com.example.ctchat.R.layout.item_sent_message);
      sKeys.put("layout/item_user_0", com.example.ctchat.R.layout.item_user);
      sKeys.put("layout/item_user_selectable_0", com.example.ctchat.R.layout.item_user_selectable);
    }
  }
}
