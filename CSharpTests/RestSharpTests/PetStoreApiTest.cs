using NUnit.Framework;
using RestSharp;
using System.Threading.Tasks;

namespace PetStoreApiTests
{
    [TestFixture]
    public class PetStoreApiTest
    {
        private const string BaseUrl = "https://petstore.swagger.io/v2";
        private const long PetId = 999999;
        private RestClient _client;

        [OneTimeSetUp]
        public void Setup()
        {
            _client = new RestClient(BaseUrl);
        }

        [OneTimeTearDown]
        public void TearDown()
        {
            _client?.Dispose();
        }

        [Test, Order(1)]
        public async Task TestPost_CreatePet()
        {
            var request = new RestRequest("pet", Method.Post);
            request.AddJsonBody(new
            {
                id = PetId,
                name = "TestPet",
                status = "available"
            });

            var response = await _client.ExecuteAsync<PetResponse>(request);

            Assert.That(response.StatusCode, Is.EqualTo(System.Net.HttpStatusCode.OK));
            Assert.That(response.Data, Is.Not.Null, "Response data is null");
            Assert.That(response.Data.Id, Is.EqualTo(PetId));
            Assert.That(response.Data.Name, Is.EqualTo("TestPet"));
        }

        [Test, Order(2)]
        public async Task TestGet_FetchPetById()
        {
            var request = new RestRequest($"pet/{PetId}", Method.Get);

            var response = await _client.ExecuteAsync(request);

            Assert.That(response.StatusCode, Is.EqualTo(System.Net.HttpStatusCode.OK));
        }

        [Test, Order(3)]
        public async Task TestPut_UpdatePet()
        {
            var request = new RestRequest("pet", Method.Put);
            request.AddJsonBody(new
            {
                id = PetId,
                name = "UpdatedPet",
                status = "sold"
            });

            var response = await _client.ExecuteAsync<PetResponse>(request);

            Assert.That(response.StatusCode, Is.EqualTo(System.Net.HttpStatusCode.OK));
            Assert.That(response.Data, Is.Not.Null, "Response data is null");
            Assert.That(response.Data.Name, Is.EqualTo("UpdatedPet"));
            Assert.That(response.Data.Status, Is.EqualTo("sold"));
        }

        [Test, Order(4)]
        public async Task TestDelete_RemovePet()
        {
            var request = new RestRequest($"pet/{PetId}", Method.Delete);

            var response = await _client.ExecuteAsync(request);

            Assert.That(response.StatusCode, Is.EqualTo(System.Net.HttpStatusCode.OK));
        }

        public class PetResponse
        {
            public long Id { get; set; }
            public string? Name { get; set; }
            public string? Status { get; set; }
        }
    }
}
