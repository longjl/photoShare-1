/**
 * 
 */
package com.photoshare.service.users.views;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.photoshare.fragments.BaseFragment;
import com.photoshare.service.follow.FollowType;
import com.photoshare.service.users.UserInfo;
import com.photoshare.tabHost.R;
import com.photoshare.view.UserHomeTitleBarView;

/**
 * @author Aron
 * 
 *         UserHomeTitleBarFragment displays views contained in
 *         {@link UserHomeTitleBarView} and handle accompanied operations.
 * 
 */
public class UserHomeTitleBarFragment extends BaseFragment {

	private UserHomeTitleBarView barView;

	public static UserHomeTitleBarFragment newInstance(int fragmentViewId) {
		UserHomeTitleBarFragment uhtbf = new UserHomeTitleBarFragment();
		uhtbf.setFragmentViewId(fragmentViewId);
		return uhtbf;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.user_home_title_bar, container, false);
	}

	private void initView() {
		barView = new UserHomeTitleBarView(((View) getActivity().findViewById(
				R.id.userHomeTitlebarLayoutId)), user.getUserInfo(), async);
		barView.registerCallback(mCallback);
		barView.applyView();
	}

	private Bundle generate() {
		Bundle args = new Bundle();
		Bundle bundle = getArguments();
		if (bundle.containsKey(KEY_WRAPPER_ID)) {
			args.putInt(KEY_WRAPPER_ID, bundle.getInt(KEY_WRAPPER_ID));
		}
		if (bundle.containsKey(KEY_WRAPPED_ID)) {
			args.putIntArray(KEY_WRAPPED_ID, bundle.getIntArray(KEY_WRAPPED_ID));
		}
		return args;
	}

	private UserHomeTitleBarView.ICallback mCallback = new UserHomeTitleBarView.ICallback() {

		public void OnPhotosCntClick() {
			// do nothing
		}

		public void OnFollowingCntClick() {
			Bundle param = generate();
			param.putParcelable(UserInfo.KEY_USER_INFO, user.getUserInfo());
			param.putString(UserInfo.KEY_FOLLOW_TYPE,
					FollowType.FOLLOWING.toString());
			forward(getFollowingFragment(), param);
		}

		public void OnFollowerCntClick() {
			Bundle param = generate();
			param.putParcelable(UserInfo.KEY_USER_INFO, user.getUserInfo());
			param.putString(UserInfo.KEY_FOLLOW_TYPE,
					FollowType.FOLLOWER.toString());
			forward(getFollowingFragment(), param);
		}

		public void OnEditInfoClick() {
			forward(getProfileFragment(), generate());
		}

		public void OnUserHeadLoaded(final ImageView imageView,
				final Drawable photo, String url) {
			getActivity().runOnUiThread(new Runnable() {

				public void run() {
					imageView.setImageDrawable(photo);
				}
			});
		}

		public void OnDefault(final ImageView imageView) {
			getActivity().runOnUiThread(new Runnable() {

				public void run() {
					imageView.setImageResource(R.drawable.icon);
				}
			});
		}
	};

	private String getFollowingFragment() {
		return getString(R.string.ffollowInfoFragment);
	}

	private String getProfileFragment() {
		return getString(R.string.fpersonalProfileFragment);
	}

	private String getPreferencesSettingsFragment() {
		return getString(R.string.fpreferenceSettingsFragment);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.photoshare.fragments.BaseFragment#OnRightBtnClicked()
	 */
	@Override
	protected void onRightBtnClicked() {
		forward(getPreferencesSettingsFragment(), null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.photoshare.fragments.BaseFragment#OnLeftBtnClicked()
	 */
	@Override
	protected void onLeftBtnClicked() {
		// do nothing
	}

	@Override
	protected void onLoginSuccess() {
		// TODO Auto-generated method stub
	}
}