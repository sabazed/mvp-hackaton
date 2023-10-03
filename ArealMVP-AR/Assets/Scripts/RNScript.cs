using System.Collections.Generic;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Text;
using Messages;
using Newtonsoft.Json;
using UnityEngine;


public class RNScript : MonoBehaviour
{

    private string username = "sabaaaaa";
    private string token = null;
    private string url = "https://areal-169b9e51c736.herokuapp.com";
    // private string url = "http://localhost:8080";
    private string statueName = "KIU PARKING F";
    private double latitude;
    private double longitude;
    private double altitude;
    private double degree;
    private bool isInitial = true;
    
    private HttpClient client;
    
    // public Gateway Gateway;
    
    private static RNScript Instance;
        
    private void Start()
    {
        if (Instance == null) {
            Instance = this;
            DontDestroyOnLoad(this);
        }
        else if (Instance != this)
        {
            Destroy(gameObject);
        }
        
        client = new HttpClient();
        // Login();
        // MessageRN("{\"username\":\"sabaaaaa\",\"token\":\"Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzYWJhYWFhYSIsImlhdCI6MTY5MzU2MDg5MiwiZXhwIjoxNjkzNjQ3MjkyfQ.tfUR12iLkEK3Gt6uKjL5CnzMV-mpRfsCVcH2Uslaz-nBaQShWUdb_n_cPx-xcEcvRZVmBqKy7poOGMVSwsu0CQ\",\"statueName\":\"Ureki Beatch\",\"latitude\":41.983129,\"longitude\":41.759593,\"altitude\":0,\"degree\":0}");
    }

    public async void Login()
    {
        var body = new StringContent("{ " + $"\"username\": \"{username}\", \"password\": \"11111111\"" + " }", Encoding.UTF8, "application/json");
        var response = await client.PostAsync(url + "/user/login/", body);
        response.EnsureSuccessStatusCode();
        var responseBody = await response.Content.ReadAsStringAsync();
        UserLoginResponse result = JsonConvert.DeserializeObject<UserLoginResponse>(responseBody);
        token = result.Token;
    }

    public List<CloudDto> FetchClouds()
    {
        // if (token == null) return null;
        string result = client.GetStringAsync(url + $"/statues/{statueName}/?username={username}&isInitial={isInitial}").Result;
        GetCloudsResponse response = JsonConvert.DeserializeObject<GetCloudsResponse>(result);
        isInitial = false;
        // Gateway.SendNativeMessage(result);
        return response.Clouds;
    }

    public void PublishCloud(string svg)
    {
        // if (token == null) return;
        Debug.Log(svg);
        Debug.Log(svg.Replace("\"", "\\\""));
        var body = new StringContent("{ " + $"\"username\": \"{username}\", \"statueName\": \"{statueName}\", \"svgContent\": \"{svg.Replace("\"", "\\\"")}\"" + " }", Encoding.UTF8, "application/json");
        var response = client.PostAsync(url + $"/statues/{statueName}/", body);
        SceneSwitcher.ChangeScene();
        // Gateway.SendNativeMessage(response.Result.StatusCode.ToString());
    }

    public void MessageRN(string message)
    {
        print("UNITY Recived message: " + message);
        AuthDataMessage authDataMessage = JsonConvert.DeserializeObject<AuthDataMessage>(message);
        if (authDataMessage != null)
        {
            username = authDataMessage.Username;
            token = authDataMessage.Token;
            url = authDataMessage.URL;
            statueName = authDataMessage.StatueName;
            latitude = authDataMessage.Latitude;
            longitude = authDataMessage.Longitude;
            altitude = authDataMessage.Altitude;
            degree = authDataMessage.Degree;
            isInitial = true;

            var tokenArr = token.Split(" ");
            client.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue(tokenArr[0], tokenArr[1]);
        }
    }
    
    public string StatueName
    {
        get => statueName;
    }

    public double Latitude
    {
        get => latitude;
    }

    public double Longitude
    {
        get => longitude;
    }

    public double Altitude
    {
        get => altitude;
    }

    public double Degree
    {
        get => degree;
    }
}
