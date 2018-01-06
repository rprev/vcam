package com.example.rprev.vcam;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ravy on 2018/01/05.
 */

public class SettingFragment extends Fragment {

    private final String PARENT_KEY_LABEL="LABEL";
    private final String CHILD_KEY_LABEL = "LABEL";
    private final String CHILD_KEY_TEXT = "TEXT";

    List<Map<String, String>> parentList;
    List<List<Map<String, String>>> allChildList;

    private ExpandableListView expandableListViewSetting;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        SharedPreferences prefer = getContext().getSharedPreferences("SaveData", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefer.edit();

        for(Map<String,String> v : parentList) {
            System.out.println(v.get(PARENT_KEY_LABEL));
//            editor.putString(v.get(PARENT_KEY_LABEL));
        }




//        editor.putString("a", editTextAccessKey.getText().toString());
 //       editor.putString("s", editTextPrivateKey.getText().toString());
  //      editor.commit();
    }

    private void addParent(String label){
        Map<String, String> parentData = new HashMap<String, String>();
        parentData.put(PARENT_KEY_LABEL, label );
        parentList.add(parentData);
    }

    private void addChildren(String... labels){
        List<Map<String, String>> childList= new ArrayList<Map<String, String>>();

        for(String label : labels) {
            Map<String, String> childData = new HashMap<String, String>();
            childData.put(CHILD_KEY_LABEL, label);
            childList.add(childData);
        }

        allChildList.add(childList);
    }


    // Viewが生成し終わった時に呼ばれるメソッド
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        expandableListViewSetting = view.findViewById(R.id.expandableListViewSetting);

        // 親リストに表示する内容を生成
        parentList = new ArrayList<Map<String, String>>();
        addParent("CoinCheck");
        addParent("bitFlyer");

        // 子要素として表示する文字を生成
        allChildList = new ArrayList<List<Map<String, String>>>();
        addChildren("アクセスキー","プライベートキー");
        addChildren("アクセスキー","プライベートキー");

        SimpleExpandableListAdapter adapter =
                new SimpleExpandableListAdapter(
                        getContext(),
                        parentList,
                        android.R.layout.simple_expandable_list_item_1,
                        new String[]{CHILD_KEY_LABEL},
                        new int[]{android.R.id.text1, android.R.id.text2},
                        allChildList,
                        R.layout.editable_expandable_list_item,
                        new String[]{CHILD_KEY_LABEL},
                        new int[]{R.id.textViewLabel}
                );

        expandableListViewSetting.setAdapter(adapter);


    }

}
