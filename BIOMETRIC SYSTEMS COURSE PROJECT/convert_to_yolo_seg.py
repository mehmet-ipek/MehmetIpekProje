import numpy as np
import cv2
import os
import shutil

# 1. VERİ YÜKLEME VE KONTROL
data_path = "np_data/img_real.npy"
if not os.path.exists(data_path):
    print(f"❌ HATA: {data_path} dosyası bulunamadı!")
    exit()

X_all = np.load(data_path)
total_available = len(X_all)

print(f"📊 Bilgi: NPY dosyasının içinde TOPLAM {total_available} adet resim var.")

# Eğer dosyada 80'den az varsa olanın hepsini al, 80 varsa 80 al
limit = min(80, total_available)
X = X_all[:limit]

img_out = "dataset_yolo/images/train"
lbl_out = "dataset_yolo/labels/train"

# Klasörleri temizle
if os.path.exists(img_out): shutil.rmtree(img_out)
if os.path.exists(lbl_out): shutil.rmtree(lbl_out)
os.makedirs(img_out, exist_ok=True)
os.makedirs(lbl_out, exist_ok=True)

print(f"🔄 {len(X)} adet veri dönüştürülüyor...")

for i, img in enumerate(X):
    img = img.reshape(160, 160)
    img_uint8 = (img * 255).astype(np.uint8)

    blur = cv2.medianBlur(img_uint8, 5)
    _, mask = cv2.threshold(blur, 0, 255, cv2.THRESH_BINARY + cv2.THRESH_OTSU)
    mask = 255 - mask 

    contours, _ = cv2.findContours(mask, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
    if len(contours) > 0:
        c = max(contours, key=cv2.contourArea)
        epsilon = 0.008 * cv2.arcLength(c, True)
        approx = cv2.approxPolyDP(c, epsilon, True)

        polygon = []
        for point in approx:
            x, y = point[0]
            polygon.append(round(x / 160, 6))
            polygon.append(round(y / 160, 6))

        if len(polygon) >= 6:
            cv2.imwrite(f"{img_out}/{i}.png", img_uint8)
            with open(f"{lbl_out}/{i}.txt", "w") as f:
                f.write("0 " + " ".join(map(str, polygon)))

final_count = len(os.listdir(img_out))
print(f"✔ İşlem bitti. Klasörde {final_count} adet dosya oluşturuldu.")