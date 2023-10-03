namespace Messages
{
    public class CloudDto
    {
        private string statueName;
        private string svgContent;

        public CloudDto()
        {
        }

        public CloudDto(string statueName, string svgContent)
        {
            this.statueName = statueName;
            this.svgContent = svgContent;
        }

        public string StatueName
        {
            get => statueName;
            set => statueName = value;
        }

        public string SvgContent
        {
            get => svgContent;
            set => svgContent = value;
        }
    }
}