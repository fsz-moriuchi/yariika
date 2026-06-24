package model;

import dao.FacilitiesDAO;
import dao.UsersDAO;
import util.PasswordUtil;

public class PasswordEditLogic {

	public PasswordEdit execute(String userId, String facilityId, String oldPassword, String newPassword,
			String newPasswordConfirm) {

		if (userId == null && facilityId == null) {
			return new PasswordEdit(false, "ログイン情報がありません。");
		}

		if (!newPassword.equals(newPasswordConfirm)) {
			return new PasswordEdit(false, "新しいパスワードが一致しません");
		}

		String oldPasswordHash = PasswordUtil.hashPassword(oldPassword);
		String newPasswordHash = PasswordUtil.hashPassword(newPassword);

		//顧客
		if (userId != null) {
			UsersDAO udao = new UsersDAO();
			UserLogin uLogin = new UserLogin(userId, oldPasswordHash);
			User loginUser = udao.findByLogin(uLogin);
			if (loginUser == null) {
				return new PasswordEdit(false, "元パスワードは間違っています");

			}
			User newPassUser = new User(userId, newPasswordHash);
			boolean result = udao.updateUserPassword(newPassUser);
			if (result) {
				return new PasswordEdit(true, null);
			} else {
				return new PasswordEdit(false, "(1)変更出来ませんでした。");

			}

		}
		//店舗
		if (facilityId != null) {
			FacilitiesDAO fdao = new FacilitiesDAO();
			FacilityLogin fLogin = new FacilityLogin(facilityId, oldPasswordHash);
			Facility loginFacility = fdao.findByLogin(fLogin);
			if (loginFacility == null) {
				return new PasswordEdit(false, "元パスワードは間違っています");

			}
			Facility newPassFacility = new Facility(facilityId, newPasswordHash);
			boolean result = fdao.updateFacilityPassword(newPassFacility);
			if (result) {
				return new PasswordEdit(true, null);
			} else {
				return new PasswordEdit(false, "1.1 - 変更出来ませんでした。");

			}

		}
		return new PasswordEdit(false, "1.2 - 変更出来ませんでした。");

	}

}
