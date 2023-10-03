namespace Messages
{
    public class UserLoginResponse
    {
        private string username;
        private string token;
        private string rejectReason;

        public UserLoginResponse()
        {
        }

        public UserLoginResponse(string username, string token, string rejectReason)
        {
            this.username = username;
            this.token = token;
            this.rejectReason = rejectReason;
        }

        public string Username
        {
            get => username;
            set => username = value;
        }

        public string Token
        {
            get => token;
            set => token = value;
        }

        public string RejectReason
        {
            get => rejectReason;
            set => rejectReason = value;
        }
    }
}