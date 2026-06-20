import numpy as np
import cv2
import os

# Klasör kontrolü
if not os.path.exists("np_data"):
    os.makedirs("np_data")

# 1. HAM VERİLERİ YÜKLE
# img_train.npy genellikle DNN eğitimi için kullanılan 150+ resmi içerir
images = np.load('np_data/img_train.npy')
print(f"📊 Toplam {len(images)} resim işleniyor...")

processed_images = []
masks = []

for img in images:
    img = img.reshape(160, 160)

    # Normalize (0-1 aralığına çekme)
    img_norm = img / 255.0 if img.max() > 1 else img

    # OpenCV işlemleri için uint8 formatına çevirme
    img_uint8 = (img_norm * 255).astype(np.uint8)

    # Otsu Eşikleme (Parmak izi çizgilerini ayırır)
    _, mask = cv2.threshold(img_uint8, 0, 255, cv2.THRESH_BINARY + cv2.THRESH_OTSU)

    # Morphology (Gürültü temizleme)
    kernel = np.ones((3,3), np.uint8)
    mask = cv2.morphologyEx(mask, cv2.MORPH_CLOSE, kernel)

    # Maskeyi tekrar 0-1 aralığına çekme (DNN'in y_train beklentisi)
    mask_norm = mask / 255.0

    processed_images.append(img_norm)
    masks.append(mask_norm)

# Listeleri Numpy array formatına ve modele uygun boyuta (batch, 160, 160, 1) getir
X_data = np.array(processed_images).reshape(-1, 160, 160, 1)
y_data = np.array(masks).reshape(-1, 160, 160, 1)

# 2. DOSYALARI KAYDET
# DNN Eğitimi için (X_train ve y_train)
np.save("np_data/X_train.npy", X_data)
np.save("np_data/y_train.npy", y_data)

# YOLO'nun beslendiği ana kaynak (img_real.npy)
np.save("np_data/img_real.npy", X_data) 

print(f"✅ İşlem Tamamlandı:")
print(f" - DNN için: X_train.npy ve y_train.npy ({len(X_data)} resim)")
print(f" - YOLO için: img_real.npy ({len(X_data)} resim) güncellendi.")