import unittest
import requests

BASE_URL = "https://petstore.swagger.io/v2"
PET_ID = 999999

class TestPetStoreApi(unittest.TestCase):

    def test_1_post_create_pet(self):
        url = f"{BASE_URL}/pet"
        payload = {
            "id": PET_ID,
            "name": "TestPet",
            "status": "available"
        }
        response = requests.post(url, json=payload)
        self.assertEqual(response.status_code, 200)
        data = response.json()
        self.assertEqual(data["id"], PET_ID)
        self.assertEqual(data["name"], "TestPet")
        print("CreatePet Response:", data)

    def test_2_get_fetch_pet_by_id(self):
        url = f"{BASE_URL}/pet/{PET_ID}"
        response = requests.get(url)
        self.assertEqual(response.status_code, 200)
        print("FetchPetById Response:", response.json())

    def test_3_put_update_pet(self):
        url = f"{BASE_URL}/pet"
        updated_payload = {
            "id": PET_ID,
            "name": "UpdatedPet",
            "status": "sold"
        }
        response = requests.put(url, json=updated_payload)
        self.assertEqual(response.status_code, 200)
        data = response.json()
        self.assertEqual(data["name"], "UpdatedPet")
        self.assertEqual(data["status"], "sold")
        print("UpdatePet Response:", data)

    def test_4_delete_remove_pet(self):
        url = f"{BASE_URL}/pet/{PET_ID}"
        response = requests.delete(url)
        self.assertEqual(response.status_code, 200)
        print("DeletePet Response:", response.json())

if __name__ == "__main__":
    unittest.main()
