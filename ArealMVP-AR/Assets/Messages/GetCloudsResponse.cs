using System.Collections.Generic;

namespace Messages
{
    public class GetCloudsResponse
    {
        
        private string username;
        private string statueName;
        private List<CloudDto> clouds;
        private string rejectReason;

        public GetCloudsResponse()
        {
        }

        public GetCloudsResponse(string username, string statueName, List<CloudDto> clouds, string rejectReason)
        {
            this.username = username;
            this.statueName = statueName;
            this.clouds = clouds;
            this.rejectReason = rejectReason;
        }

        public string Username
        {
            get => username;
            set => username = value;
        }

        public string StatueName
        {
            get => statueName;
            set => statueName = value;
        }

        public List<CloudDto> Clouds
        {
            get => clouds;
            set => clouds = value;
        }

        public string RejectReason
        {
            get => rejectReason;
            set => rejectReason = value;
        }
    }
}