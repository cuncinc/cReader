package com.cc.creader.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cc.creader.activity.LoginActivity;
import com.cc.creader.R;

import java.io.File;

import static com.cc.creader.activity.MainActivity.dbmanager;

public class PersonInfoFragment extends Fragment
{
    private Cursor cursor;
    private BottomSheetDialog dialog;
    private String onlineID;
    private String onlineAccount;
    private String profile_route;
//    private String onlineID;
//    private String onlineAccount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Log.e("个人中心", "onCreateView");
        View view = inflater.inflate(R.layout.person_infor_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        Log.e("个人中心", "onActivityCreated");
        String command_get_onlineAccount = "SELECT * FROM AccountInfo WHERE IsOnline = 1";
        cursor = dbmanager.findDB(command_get_onlineAccount);
        cursor.moveToFirst();
        profile_route = cursor.getString(cursor.getColumnIndex("ProfileRoute"));
        onlineID = cursor.getString(cursor.getColumnIndex("ID"));
        onlineAccount = cursor.getString(cursor.getColumnIndex("AccountNumber"));

        initView();

        //退出账号监听器
        LinearLayout linearLayout = (LinearLayout) getActivity().findViewById(R.id.layout_logoff);
        linearLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String command_toLogoff = "UPDATE AccountInfo SET IsOnline = 0 WHERE IsOnline = 1;";
                dbmanager.updateDB(command_toLogoff);
                Toast.makeText(getActivity(), "退出成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                getActivity().finish();
                startActivity(intent);
            }
        });

        //修改密码监听器
        LinearLayout layout_modifypass = (LinearLayout) getActivity().findViewById(R.id.layout_modifypass);
        layout_modifypass.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showModiPassDialog();
            }
        });
    }

    private void initView()
    {
        //设置头像
        ImageView ima_profile = (ImageView) getActivity().findViewById(R.id.image_profile);
        if (profile_route==null || profile_route.length()==0 || !fileIsExists(profile_route)) //如果ProfileRoute为null或空，即默认头像没有被替换；或者文件不存在
        {
            ima_profile.setImageResource(R.drawable.default_profile);
        }
        else
        {
            //如果默认头像被替换了，用被替换掉的头像的路径
            Bitmap bitmap = BitmapFactory.decodeFile(profile_route);
            ima_profile.setImageBitmap(bitmap);
        }
        //设置ID
        TextView tv_id = (TextView) getActivity().findViewById(R.id.textview_online_id);
        tv_id.setText(onlineID);
        //设置账号
        TextView tv_account = (TextView) getActivity().findViewById(R.id.textview_online_account);
        tv_account.setText(onlineAccount);
    }

    public boolean fileIsExists(String strFile)
    {
        try
        {
            File f = new File(strFile);
            if (!f.exists())
            {
                return false;
            }
        }
        catch (Exception e)
        {
            return false;
        }
        return true;
    }

    private void showModiPassDialog()
    {
        dialog = new BottomSheetDialog(getActivity());
        View commentView = LayoutInflater.from(getActivity()).inflate(R.layout.modi_pass_layout,null);
        final EditText et_oldpass = (EditText) commentView.findViewById(R.id.editText_oldPass);
        final EditText et_newpass = (EditText) commentView.findViewById(R.id.editText_newPass);
        final EditText et_newpass_again = (EditText) commentView.findViewById(R.id.editText_newPass_again);
        Button bt_modipass = (Button) commentView.findViewById(R.id.button_modi_pass);
        dialog.setContentView(commentView);
        //显示不全问题
        View parent = (View) commentView.getParent();
        BottomSheetBehavior behavior = BottomSheetBehavior.from(parent);
        commentView.measure(0,0);
        behavior.setPeekHeight(commentView.getMeasuredHeight());
        bt_modipass.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String oldpass = et_oldpass.getText().toString();
                String newpass = et_newpass.getText().toString();
                String newpass_again = et_newpass_again.getText().toString();
                String passInDB = cursor.getString(cursor.getColumnIndex("PassWord"));

                if(!passInDB.equals(oldpass))
                {
                    Toast.makeText(getActivity(),"原密码错误，请重新输入",Toast.LENGTH_SHORT).show();
                }
                else if (!newpass.equals(newpass_again))
                {
                    Toast.makeText(getActivity(),"两次密码不同，请重新输入",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    dialog.dismiss();
                    String command_update_pass = "UPDATE AccountInfo SET PassWord = \'" + newpass + "\' WHERE ID = " + onlineID;
                    dbmanager.updateDB(command_update_pass);
                    Toast.makeText(getActivity(),"修改成功",Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }

//    @Override
//    public void onStart()
//    {
//        super.onStart();
//        Log.e("个人中心", "onStart");
//    }
//
//    @Override
//    public void onDestroy()
//    {
//        super.onDestroy();
//        Log.e("个人中心", "onDestroy");
//    }
//
//    @Override
//    public void onAttach(Context context)
//    {
//        super.onAttach(context);
//        Log.e("个人中心", "onAttach");
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//        Log.e("个人中心", "onCreate");
//    }
//
//    @Override
//    public void onPause()
//    {
//        super.onPause();
//        Log.e("个人中心", "onPause");
//    }
//
//    @Override
//    public void onResume()
//    {
//        super.onResume();
//        Log.e("个人中心", "onResume");
//    }
//
//    @Override
//    public void onStop()
//    {
//        super.onStop();
//        Log.e("个人中心", "onStop");
//    }
//
//    @Override
//    public void onDestroyView()
//    {
//        super.onDestroyView();
//        Log.e("个人中心", "onDestroyView");
//    }
//
//    @Override
//    public void onDetach()
//    {
//        super.onDetach();
//        Log.e("个人中心", "onDetach");
//    }
}
