package bank;

public class UserHolder {

        private static UserHolder instance = null;
        private String userName = "";
        /**
         * @return the userName
         */
        public String getUserName() {
            return userName;
        }
        /**
         * @param userName the userName to set
         */
        public void setUserName(String userName) {
            this.userName = userName;
        }

        private UserHolder() {}
        public static UserHolder getInstance() {
            if(instance == null)
                instance = new UserHolder();
            return instance;
        }

}
