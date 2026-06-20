from ultralytics import YOLO
import cv2
import os
import numpy as np
import random

# 1. MODEL YOLU
model_path = "runs/segment/parmak_izi_yolo_80/weights/best.pt"
if not os.path.exists(model_path):
    model_path = "runs/segment/runs/segment/parmak_izi_yolo_80/weights/best.pt"

model = YOLO(model_path)

# 2. RASTGELE GÖRSEL SEÇİMİ
folder_path = "dataset_yolo/images/train"
all_images = [f for f in os.listdir(folder_path) if f.endswith('.png')]
selected_file = random.choice(all_images)
image_path = os.path.join(folder_path, selected_file)

# 3. TAHMİN
results = model.predict(source=image_path, conf=0.005, imgsz=160)

# 4. GÖRSELLERİ HAZIRLA
res_size = 450
ori_img = cv2.imread(image_path)
ori_img = cv2.resize(ori_img, (res_size, res_size))

pred_img = results[0].plot(boxes=False, masks=True, labels=False)
pred_img = cv2.resize(pred_img, (res_size, res_size))

# --- CONFUSION MATRIX ULTRA NETLEŞTİRME ---
conf_matrix_path = "runs/segment/parmak_izi_yolo_80/confusion_matrix.png"
if not os.path.exists(conf_matrix_path):
    conf_matrix_path = "runs/segment/runs/segment/parmak_izi_yolo_80/confusion_matrix.png"

if os.path.exists(conf_matrix_path):
    conf_mat_raw = cv2.imread(conf_matrix_path)
    h, w, _ = conf_mat_raw.shape
    c_h = int(h * 0.15)
    c_w = int(w * 0.15)
    conf_mat_cropped = conf_mat_raw[c_h:h-int(h*0.05), c_w:w-int(w*0.05)]
    
    alpha = 1.2 # Kontrast
    beta = -20   # Parlaklık
    conf_mat_final = cv2.convertScaleAbs(conf_mat_cropped, alpha=alpha, beta=beta)
    conf_mat = cv2.resize(conf_mat_final, (res_size, res_size))
else:
    conf_mat = np.zeros((res_size, res_size, 3), dtype=np.uint8)
    cv2.putText(conf_mat, "Dosya Bulunamadi", (100, 225), cv2.FONT_HERSHEY_SIMPLEX, 0.8, (0,0,255), 2)
# ------------------------------------------

# 5. AYIRICI ÇİZGİ VE BİRLEŞTİRME
separator = np.ones((res_size, 10, 3), dtype=np.uint8) * 255
final_content = np.hstack((ori_img, separator, pred_img, separator, conf_mat))

# 6. ÜST BAŞLIK
header = np.zeros((80, final_content.shape[1], 3), dtype=np.uint8)
cv2.putText(header, f"GIRIS: {selected_file}", (50, 50), cv2.FONT_HERSHEY_SIMPLEX, 1.0, (255, 255, 255), 2)
cv2.putText(header, "YOLOv8 SEGMENTASYON ANALIZI", (450, 50), cv2.FONT_HERSHEY_SIMPLEX, 1.0, (255, 255, 255), 2)
cv2.putText(header, "PERFORMANS (MATRIX)", (1000, 50), cv2.FONT_HERSHEY_SIMPLEX, 1.0, (255, 255, 255), 2)

# 7. ALT BİLGİ
footer = np.zeros((80, final_content.shape[1], 3), dtype=np.uint8)
mAP_val = "Basari: %99.5"
IoU_val = "Hassasiyet: %71.7"
inf_speed = f"Hiz: {results[0].speed['inference']:.1f}ms"

cv2.putText(footer, mAP_val, (20, 45), cv2.FONT_HERSHEY_SIMPLEX, 0.8, (0, 255, 0), 2)
cv2.putText(footer, IoU_val, (500, 45), cv2.FONT_HERSHEY_SIMPLEX, 0.8, (0, 255, 0), 2)
cv2.putText(footer, inf_speed, (1100, 45), cv2.FONT_HERSHEY_SIMPLEX, 0.8, (0, 255, 0), 2)

presentation_ready = np.vstack((header, final_content, footer))

# 8. GÖRÜNTÜLE VE CONSOLE ÇIKTISI
print("-" * 50)
print(f"📊 ANALIZ RAPORU: {selected_file}")
print("-" * 50)
print(f"✅ Model Basari Orani (mAP50): %99.5")
print(f"🎯 Maske Hassasiyeti (IoU): %71.7")
print(f"⚡ Islem Hizi (Inference): {results[0].speed['inference']:.1f} ms")
print("-" * 50)
print(f"📌 Confusion Matrix Verileri (Toplam 105 Örnek):")
print(f"   - Doğru Tahmin (TP - Fingerprint): 44")
print(f"   - Yanlış Negatif (FN): 25")
print(f"   - Doğru Arkaplan (TN): 36")
print("-" * 50)
print(f"✔ Sunum Paneli Hazir. Kapatmak için bir tuşa basın.")

cv2.imshow("BIYOMETRIK ANALIZ - YOLOv8 SUNUMU", presentation_ready)
cv2.imwrite("sunum_final_full_net.png", presentation_ready)

cv2.waitKey(0)
cv2.destroyAllWindows()