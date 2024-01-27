import requests
from PIL import Image
import io
import os
import random
import time


BASE_URL = "https://monitoring.e-kassa.gov.az/pks-monitoring/2.0.0/documents/"
MIN_SIZE = 4000


def main():
    while True:
        image_url = BASE_URL + get_random_string(12)
        print(image_url)

        ip = get_public_ip()
        print(ip)

        response = requests.get(image_url)
        if response.status_code == 200:
            image = Image.open(io.BytesIO(response.content))

            if image.height > 164:
                print(image.height)

                file_name = "downloaded_image" + get_random_string(12) + ".jpeg"
                image.save(file_name, format="JPEG")
            else:
                print("The image is too small to download.")

        time.sleep(1)


def get_random_string(length):
    chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
    return ''.join(random.choice(chars) for i in range(length))


def get_public_ip():
    response = requests.get("http://checkip.amazonaws.com")
    return response.text.strip()


if __name__ == "__main__":
    main()
