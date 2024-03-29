package com.cc.creader.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import com.bumptech.glide.Glide;
import com.cc.creader.activity.LoginActivity;
import com.cc.creader.R;
import com.jackandphantom.blurimage.BlurImage;

import java.io.File;

import static android.app.Activity.RESULT_OK;
import static com.cc.creader.activity.MainActivity.dbmanager;

public class PersonInfoFragment extends Fragment
{
    private Cursor cursor;
    private BottomSheetDialog dialog;
    private String onlineID;
    private String onlineAccount;
    private String profile_route;
    private ImageView ima_profile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
//        Log.e("个人中心", "onCreateView");
        View view = inflater.inflate(R.layout.fragment_person_info, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
//        Log.e("个人中心", "onActivityCreated");

        initView(); //初始化数据信息
        //修改头像
        ima_profile.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showUpdateProfileDialog();
            }
        });

        //修改签名
        LinearLayout layout_modi_signature = (LinearLayout) getActivity().findViewById(R.id.layout_signature);
        layout_modi_signature.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showUpdatePassSignature();
            }
        });

        //修改密码
        LinearLayout layout_modifypass = (LinearLayout) getActivity().findViewById(R.id.layout_modifypass);
        layout_modifypass.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showUpdatePassDialog();
            }
        });

        //退出账号
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
                startActivity(intent);
                getActivity().finish();
            }
        });
    }

    private void initView()
    {
        String command_get_onlineAccount = "SELECT * FROM AccountInfo WHERE IsOnline = 1";
        cursor = dbmanager.findDB(command_get_onlineAccount);
        cursor.moveToFirst();
        profile_route = cursor.getString(cursor.getColumnIndex("ProfileRoute"));
        onlineID = cursor.getString(cursor.getColumnIndex("ID"));
        onlineAccount = cursor.getString(cursor.getColumnIndex("AccountNumber"));
        String signature = cursor.getString(cursor.getColumnIndex("Signature"));
        if (signature==null || signature.length()==0)
        {
            ;
        }
        else
        {
            TextView tv_signature = (TextView) getActivity().findViewById(R.id.textView_signature);
            tv_signature.setText(signature);
        }

        ima_profile = (ImageView) getActivity().findViewById(R.id.image_profile);

        //设置ID
        TextView tv_id = (TextView) getActivity().findViewById(R.id.textview_online_id);
        tv_id.setText(onlineID);
        //设置账号
        TextView tv_account = (TextView) getActivity().findViewById(R.id.textview_online_account);
        tv_account.setText(onlineAccount);
        //设置头像
        setProfile(profile_route);
    }

    private void showUpdatePassSignature()
    {
        dialog = new BottomSheetDialog(getActivity());
        View commentView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_modi_signature, null);
        final EditText et_signature = (EditText) commentView.findViewById(R.id.editText_signature);
        Button bt_signature = (Button) commentView.findViewById(R.id.button_get_signature);
        dialog.setContentView(commentView);
        final TextView tv_signature = (TextView) getActivity().findViewById(R.id.textView_signature);
        bt_signature.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
                String modi_signature = et_signature.getText().toString();
                if (modi_signature.length()==0)
                {
                    Toast.makeText(getActivity(), "签名不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                String command_update_signature = "UPDATE AccountInfo SET Signature = \'" + modi_signature + "\' WHERE  IsOnline = 1;";
                dbmanager.updateDB(command_update_signature);
                tv_signature.setText(modi_signature);
            }
        });
        dialog.show();
    }

    private void showUpdateProfileDialog()
    {
        dialog = new BottomSheetDialog(getActivity());
        View commentView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_modi_profile,null);
        Button bt_modiprofile = (Button) commentView.findViewById(R.id.button_modi_profile);
        dialog.setContentView(commentView);
        bt_modiprofile.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 2);  //唯一即可
            }
        });
        dialog.show();
    }

    private void showUpdatePassDialog()
    {
        dialog = new BottomSheetDialog(getActivity());
        View commentView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_modi_pass,null);
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
                    String command_update_pass = "UPDATE AccountInfo SET IsRememberPassword = 0, PassWord = \'" + newpass + "\' WHERE ID = " + onlineID;
                    dbmanager.updateDB(command_update_pass);
                    Toast.makeText(getActivity(),"密码修改成功！",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            }
        });
        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        //在相册里面选择好相片之后调回到现在的这个activity中
        switch (requestCode)
        {
            case 2:
            if (resultCode == RESULT_OK)
            {
                try
                {
                    Uri selectedImage = data.getData(); //获取系统返回的照片的Uri
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);//从系统表中查询指定Uri对应的照片
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    final String path = cursor.getString(columnIndex);  //获取照片路径
                    cursor.close();
                    if (setProfile(path))
                    {
                        String command_update_profile = "UPDATE AccountInfo SET ProfileRoute = \'" + path + "\' WHERE ID = " + onlineID;
                        dbmanager.updateDB(command_update_profile);
                        Toast.makeText(getActivity(), "更换头像成功！", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            break;
        }
    }

    private boolean setProfile(String route)
    {
        ImageView image_background = (ImageView)getActivity().findViewById(R.id.image_background);
        Bitmap bitmap = BitmapFactory.decodeFile(route);
        BlurImage.with(getActivity()).load(bitmap).intensity(250).Async(true).into(image_background);
        try
        {
            Glide.with(getActivity()).load(route).placeholder(R.drawable.default_profile).error(R.drawable.default_profile).into(ima_profile);
        }
        catch (Exception e)
        {
            return false;
        }
        return true;
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
////
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
