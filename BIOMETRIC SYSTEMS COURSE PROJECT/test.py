import numpy as np
import tensorflow as tf
import matplotlib.pyplot as plt
from sklearn.metrics import confusion_matrix, classification_report, accuracy_score
import seaborn as sns
import time

def iou_metric(y_true, y_pred):
    y_pred = tf.cast(y_pred > 0.5, tf.float32)
    intersection = tf.reduce_sum(y_true * y_pred)
    union = tf.reduce_sum(y_true) + tf.reduce_sum(y_pred) - intersection
    return intersection / (union + 1e-7)

# 1. MODEL VE VERİ YÜKLEME
model = tf.keras.models.load_model("fingerprint_model.keras", custom_objects={'iou': iou_metric})
X_test = np.load('np_data/X_train.npy').astype('float32')
y_test = np.load('np_data/y_train.npy').astype('float32')

# Rastgele bir örnek seçelim
idx = np.random.randint(0, len(X_test))
sample_x = X_test[idx].reshape(1,160,160,1)
sample_y = y_test[idx]

# 2. TAHMİN VE HIZ ÖLÇÜMÜ 
start_time = time.time()
pred = model.predict(sample_x)[0]
end_time = time.time()
inf_speed = (end_time - start_time) * 1000 # milisaniye

# Metrikleri hesapla (Görselde göstermek için)
pred_binary = (pred > 0.5).astype(np.uint8).flatten()
true_binary = (sample_y > 0.5).astype(np.uint8).flatten()
acc = accuracy_score(true_binary, pred_binary) * 100

# 3. GÖRSELLEŞTİRME 
plt.figure(figsize=(12,6))

# Sol: Confusion Matrix
plt.subplot(1,2,1)
cm = confusion_matrix(true_binary, pred_binary, labels=[0, 1])
sns.heatmap(cm, annot=True, fmt='d', cmap='Blues', xticklabels=['Arkaplan', 'Parmak Izi'], yticklabels=['Arkaplan', 'Parmak Izi'])
plt.title(f'DNN Piksel Analizi\n(Gorsel ID: #{idx} | Hiz: {inf_speed:.1f}ms)')

# Sağ: Görsel Sonuçlar
plt.figure(figsize=(15,6))
plt.suptitle(f"Analiz Sonuclari - Gorsel ID: #{idx} | Basari: %{acc:.1f} | Islem Suresi: {inf_speed:.1f}ms", fontsize=14)

plt.subplot(1,3,1); plt.imshow(sample_x[0].reshape(160,160), cmap='gray'); plt.title("Orijinal Goruntu")
plt.subplot(1,3,2); plt.imshow(pred.reshape(160,160), cmap='jet'); plt.title("DNN Tahmin (Isi Haritasi)")
plt.subplot(1,3,3); plt.imshow(sample_y.reshape(160,160), cmap='gray'); plt.title("Hedef (Gercek Maske)")

plt.tight_layout()
plt.show()

# Konsol Raporu
print("-" * 50)
print(f"📊 DNN TEST RAPORU - GORSEL #{idx}")
print("-" * 50)
print(f"⚡ Tahmin Hizi: {inf_speed:.1f} ms")
print(f"🎯 Piksel Basarisi: %{acc:.1f}")
print("-" * 50)
print(classification_report(true_binary, pred_binary, target_names=['Arkaplan', 'Parmak Izi']))