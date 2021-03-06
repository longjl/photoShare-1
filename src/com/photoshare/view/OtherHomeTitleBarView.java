/**
 * 
 */
package com.photoshare.view;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.photoshare.common.IObserver;
import com.photoshare.msg.MsgType;
import com.photoshare.service.users.UserInfo;
import com.photoshare.tabHost.R;
import com.photoshare.utils.async.AsyncImageLoader.ImageCallback;
import com.photoshare.utils.async.AsyncUtils;

/**
 * @author Aron
 * 
 *         OtherHomeTitleBarView displays views other users home titlebar.
 * 
 */
public class OtherHomeTitleBarView {

	private View baseView;
	private ImageView mUserHomeHeadView;
	private TextView mUserHomePhotosCntView;
	private UserTextView mUserHomeLikesCntView;
	private UserTextView mUserHomeFollowersCntView;
	private UserTextView mUserHomeFollowingCntView;
	private UserTextView mUserNameView;
	private TextView mUserBioView;
	private UserTextView mUserWebsiteView;
	private UserBooleanBtn mUserFollowBtn;
	private UserInfo userInfo;
	private AsyncUtils async;

	/**
	 * @param baseView
	 * @param userInfo
	 * @param async
	 */
	public OtherHomeTitleBarView(View baseView, UserInfo userInfo,
			AsyncUtils async) {
		super();
		this.baseView = baseView;
		this.userInfo = userInfo;
		this.async = async;
	}

	public void applyData(UserInfo info) {
		this.userInfo = info;
		applyView();
	}

	public void applyView() {
		mUserBioView = (TextView) baseView.findViewById(R.id.otherHomeBio);
		mUserFollowBtn = new UserBooleanBtn(baseView, R.id.otherHomeFollowBtn,
				userInfo.isFollowing(), MsgType.FOLLOW.getEnabledString(),
				MsgType.FOLLOW.getIntermediateString(),
				MsgType.FOLLOW.getDisabledString());
		mUserFollowBtn.registerListener(onObserverClickListener);
		mUserFollowBtn.applyView();

		mUserHomeFollowersCntView = new UserTextView(
				(TextView) baseView.findViewById(R.id.otherHomeFollowerCnt),
				userInfo, userInfo.getFollowersCnt() + "\r\n 跟随者");
		mUserHomeFollowersCntView.registerListener(followerCntListener);

		mUserHomeFollowingCntView = new UserTextView(
				(TextView) baseView.findViewById(R.id.otherHomeFollowingCnt),
				userInfo, userInfo.getFollowingCnt() + "\r\n 跟随");
		mUserHomeFollowingCntView.registerListener(followingCntListener);

		mUserHomeHeadView = (ImageView) baseView
				.findViewById(R.id.otherHomeHead);

		mUserHomeLikesCntView = new UserTextView(
				(TextView) baseView.findViewById(R.id.otherHomeLikesCnt),
				userInfo, userInfo.getLikesCnt() + "\r\n 喜欢");
		mUserHomeLikesCntView.registerListener(likeCntListener);

		mUserHomePhotosCntView = (TextView) baseView
				.findViewById(R.id.otherHomePhotoCnt);

		mUserWebsiteView = new UserTextView(
				(TextView) baseView.findViewById(R.id.otherHomeWebsite),
				userInfo, userInfo.getWebsite());

		mUserWebsiteView.registerListener(websiteListener);

		mUserNameView = new UserTextView(
				(TextView) baseView.findViewById(R.id.otherHomeName), userInfo,
				userInfo.getName());
		mUserNameView.registerListener(nameListener);

		mUserBioView.setText(userInfo.getBio());
		mUserHomePhotosCntView.setText(userInfo.getPhotosCnt() + "\r\n 鐓х墖");

		async.loadDrawableFromWeb(userInfo.getTinyurl(), new ImageCallback() {

			public void imageLoaded(Drawable imageDrawable, String imageUrl) {
				if (mCallback != null) {
					mCallback.OnUserHeadLoaded(mUserHomeHeadView,
							imageDrawable, imageUrl);
				}
			}

			public void imageDefault() {
				if (mCallback != null) {
					mCallback.OnDefault(mUserHomeHeadView);
				}
			}
		});

	}

	private UserBooleanBtn.OnObserverClickListener onObserverClickListener = new UserBooleanBtn.OnObserverClickListener() {

		public void OnClick(IObserver<Boolean> observer) {
			if (mCallback != null) {
				mCallback.OnFollowClick(userInfo, observer);
			}
		}
	};

	private UserTextView.UserTextOnClickListener followerCntListener = new UserTextView.UserTextOnClickListener() {

		public void OnClick(UserInfo info) {
			if (mCallback != null) {
				mCallback.OnFollowerCntClick(info);
			}
		}
	};

	private UserTextView.UserTextOnClickListener followingCntListener = new UserTextView.UserTextOnClickListener() {

		public void OnClick(UserInfo info) {
			if (mCallback != null) {
				mCallback.OnFollowingCntClick(info);
			}
		}
	};

	private UserTextView.UserTextOnClickListener likeCntListener = new UserTextView.UserTextOnClickListener() {

		public void OnClick(UserInfo info) {
			if (mCallback != null) {
				mCallback.OnLikesCntClick(info);
			}
		}
	};

	private UserTextView.UserTextOnClickListener nameListener = new UserTextView.UserTextOnClickListener() {

		public void OnClick(UserInfo info) {
			if (mCallback != null) {
				mCallback.OnNameClick(info);
			}
		}
	};

	private UserTextView.UserTextOnClickListener websiteListener = new UserTextView.UserTextOnClickListener() {

		public void OnClick(UserInfo info) {
			if (mCallback != null) {
				mCallback.OnWebsiteClick(info);
			}
		}
	};

	private ICallback mCallback;

	public void registerCallback(ICallback callback) {
		this.mCallback = callback;
	}

	public interface ICallback {
		public void OnFollowerCntClick(UserInfo info);

		public void OnFollowingCntClick(UserInfo info);

		public void OnLikesCntClick(UserInfo info);

		public void OnNameClick(UserInfo info);

		public void OnWebsiteClick(UserInfo info);

		public void OnFollowClick(UserInfo info, IObserver<Boolean> observer);

		public void OnUserHeadLoaded(ImageView imageView, Drawable photo,
				String url);

		public void OnDefault(ImageView imageView);
	}

}
