namespace Messages
{
    public class AuthDataMessage
    {
        private string username;
        private string token;
        private string url;
        private string statueName;
        private double latitude;
        private double longitude;
        private double altitude;
        private double degree;

        public AuthDataMessage()
        {
        }

        public AuthDataMessage(string username, string token, string url, string statueName, double latitude, double longitude,
            double altitude, double degree)
        {
            this.username = username;
            this.token = token;
            this.url = url;
            this.statueName = statueName;
            this.latitude = latitude;
            this.longitude = longitude;
            this.altitude = altitude;
            this.degree = degree;
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

        public string URL
        {
            get => url;
            set => url = value;
        }

        public string StatueName
        {
            get => statueName;
            set => statueName = value;
        }

        public double Latitude
        {
            get => latitude;
            set => latitude = value;
        }

        public double Longitude
        {
            get => longitude;
            set => longitude = value;
        }

        public double Altitude
        {
            get => altitude;
            set => altitude = value;
        }

        public double Degree
        {
            get => degree;
            set => degree = value;
        }
    }
}