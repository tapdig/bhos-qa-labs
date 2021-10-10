public class PermissionManager {
    private PermissionLevel mCurrentLevel = PermissionLevel.USER;

    public String getPermissionLevel() {
        String role;

        switch(mCurrentLevel) {
            case ADMIN:
                role = "Admin";
                break;
            case DEVELOPER:
                role = "Developer";
                break;
            case USER:
                role = "User";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + mCurrentLevel);
        }
        return role;
    }

    public void setPermissionLevel(PermissionLevel permissionLevel) {
        mCurrentLevel = permissionLevel;
    }
}