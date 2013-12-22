package com.isummon.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.platform.comapi.map.r;
import com.isummon.R;
import com.isummon.data.GlobalVariables;
import com.isummon.model.LogInResultType;
import com.isummon.model.RegisterResultType;
import com.isummon.model.UserModel;
import com.isummon.net.NetHelper;
import com.isummon.widget.ProgressTaskBundle;


/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 * @author xhzhang
 * @author horzwxy
 *
 */
public class LoginActivity extends Activity {

    public static final int ANIMATION_DURATION = 100;

    // Values for email and password at the time of the login attempt.
    private String mEmail;
    private String mPassword;

    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.register_forms).setTranslationX(getScreenX());

        // Set up the login form.
        mEmailView = (EditText) findViewById(R.id.login_email);
        mPasswordView = (EditText) findViewById(R.id.password);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /**
     * do signing in work
     */
    public void login(View v) {
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        mEmail = mEmailView.getEditableText().toString();
        mPassword = mPasswordView.getEditableText().toString();

        boolean shouldCancel = false;
        View focusView = null;

        // Check for a valid password.
        if (TextUtils.isEmpty(mPassword)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            shouldCancel = true;
        } else if (mPassword.length() < 4) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            shouldCancel = true;
        }

        // todo email check result overrides pwd check result
        // Check for a valid email address.
        if (TextUtils.isEmpty(mEmail)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            shouldCancel = true;
            // todo email format check
        }
//        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(mEmail).matches()) {
//            mEmailView.setError(getString(R.string.error_invalid_email));
//            focusView = mEmailView;
//            shouldCancel = true;
//        }
//        else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(mEmail).matches()) {
//            mEmailView.setError(getString(R.string.error_invalid_email));
//            focusView = mEmailView;
//            shouldCancel = true;
//
//        }
//        else if (!mEmail.contains("@")) {
//            mEmailView.setError(getString(R.string.error_invalid_email));
//            focusView = mEmailView;
//            shouldCancel = true;
//        }

        if (shouldCancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            new ProgressTaskBundle<String, LogInResultType>(
                    this,
                    R.string.login_progress_signing_in
            ) {
                @Override
                protected LogInResultType doWork(String... params) {
                    return GlobalVariables.netHelper.login(params[0], params[1]);
                }

                @Override
                protected void dealResult(LogInResultType result) {
                    switch (result) {
                        case SUCCESS:
                            Intent intent = new Intent();
                            intent.setClass(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            break;
                        case FAIL_NOT_MATCH:
                            showToast(R.string.sign_in_failed_not_match);
                            break;
                        case FAIL_TIMEOUT:
                            showToast(R.string.sign_in_failed_on_timeout);
                            break;
                    }
                }
            }.action(mEmail, mPassword);
        }
    }

    /**
     * do registering work
     */
    public void register(View v) {
        View focusView = null;
        final EditText registerEmail = (EditText) findViewById(R.id.register_email);
        final EditText registerNickname = (EditText) findViewById(R.id.register_nickname);
        final EditText registerPwd = (EditText) findViewById(R.id.register_password);
        EditText registerPwd2 = (EditText) findViewById(R.id.register_password_again);

        if(TextUtils.isEmpty(registerEmail.getText().toString())) {
            registerEmail.setError(getString(R.string.error_field_required));
            focusView = registerEmail;
        }
        else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(
                registerEmail.getEditableText().toString()).matches()) {
            registerEmail.setError(getString(R.string.error_invalid_email));
            focusView = registerEmail;
        }
        else if(TextUtils.isEmpty(registerNickname.getEditableText().toString())) {
            registerNickname.setError(getString(R.string.error_field_required));
            focusView = registerEmail;
        }
        else if(TextUtils.isEmpty(registerPwd.getEditableText().toString())) {
            registerPwd.setError(getString(R.string.error_field_required));
            focusView = registerPwd;
        }
        else if(TextUtils.isEmpty(registerPwd2.getEditableText().toString())) {
            registerPwd2.setError(getString(R.string.error_field_required));
            focusView = registerPwd2;
        }

        if(focusView != null) {
            focusView.requestFocus();
            return;
        }
        else {
            if(!registerPwd.getEditableText().toString().equals(registerPwd2.getText().toString())) {
                registerPwd.setError(getString(R.string.register_not_same_pwd));
                registerPwd.requestFocus();
                return;
            }
            if(registerPwd.getEditableText().length() < 6) {
                registerPwd.setError(getString(R.string.register_too_short_pwd));
                registerPwd.requestFocus();
                return;
            }

            new ProgressTaskBundle<String, RegisterResultType>(
                    this,
                    R.string.register_submitting
            ) {
                @Override
                protected RegisterResultType doWork(String... params) {
                    UserModel newUser = new UserModel();
                    newUser.setAvatar(0);
                    newUser.setNickName(registerNickname.getEditableText().toString());
                    newUser.setUserName(registerEmail.getEditableText().toString());
                    newUser.setPasswd(registerPwd.getEditableText().toString());
                    return GlobalVariables.netHelper.register(newUser);
                }

                @Override
                protected void dealResult(RegisterResultType result) {
                    switch (result) {
                        case SUCCESS:
                            showToast(R.string.register_success);
                            toLogIn(null);
                            break;
                        case FAIL_TIME_OUT:
                            showToast(R.string.register_failed_on_timeout);
                            break;
                        case FAIL_ON_USED_EMAIL:
                            registerEmail.setError(getString(R.string.register_failed_on_used_email));
                            registerEmail.requestFocus();
                            break;
                        case FAIL_ON_USED_NICKNAME:
                            registerNickname.setError(getString(R.string.register_failed_on_used_nickname));
                            registerNickname.requestFocus();
                            break;
                    }
                }
            }.action(registerEmail.getEditableText().toString(),
                    registerNickname.getEditableText().toString(),
                    registerPwd.getEditableText().toString());
        }
    }

    private void showToast(int msgId) {
        Toast.makeText(this,
                msgId,
                Toast.LENGTH_SHORT).show();
    }

    /**
     * transform to register view
     */
    public void toRegister(View v) {
        ValueAnimator loginAnimation = ObjectAnimator.ofFloat(
                findViewById(R.id.login_forms),
                "translationX", 0f, -getScreenX() );
        loginAnimation.setDuration(ANIMATION_DURATION);
        loginAnimation.setInterpolator(new LinearInterpolator());
        loginAnimation.start();

        ValueAnimator registerAnimation = ObjectAnimator.ofFloat(
                findViewById(R.id.register_forms),
                "translationX", getScreenX(), 0f );
        registerAnimation.setDuration(ANIMATION_DURATION);
        registerAnimation.setInterpolator(new LinearInterpolator());
        registerAnimation.start();
    }

    /**
     * transform to sign in view
     */
    public void toLogIn(View v) {
        ValueAnimator loginAnimation = ObjectAnimator.ofFloat(
                findViewById(R.id.login_forms),
                "translationX", -getScreenX(), 0f );
        loginAnimation.setDuration(ANIMATION_DURATION);
        loginAnimation.setInterpolator(new LinearInterpolator());
        loginAnimation.start();

        ValueAnimator registerAnimation = ObjectAnimator.ofFloat(
                findViewById(R.id.register_forms),
                "translationX", 0f, getScreenX() );
        registerAnimation.setDuration(ANIMATION_DURATION);
        registerAnimation.setInterpolator(new LinearInterpolator());
        registerAnimation.start();
    }

    private int getScreenX() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        return displaymetrics.widthPixels;
    }
}
