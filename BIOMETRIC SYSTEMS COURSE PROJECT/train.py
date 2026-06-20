import numpy as np
import tensorflow as tf
from model import unet

#iou gerçek maske ile tahminleme maskenin matematiksel başarı hesaplaması

def iou(y_true, y_pred):
    y_pred = tf.cast(y_pred > 0.5, tf.float32)
    intersection = tf.reduce_sum(y_true * y_pred)
    union = tf.reduce_sum(y_true) + tf.reduce_sum(y_pred) - intersection
    return intersection / (union + 1e-7)

X = np.load('np_data/X_train.npy')
y = np.load('np_data/y_train.npy')

model = unet()
model.compile(optimizer='adam', loss='binary_crossentropy', metrics=['accuracy', iou])

print("DNN Eğitimi ve Matematiksel Hesaplamalar Başlıyor...")
history = model.fit(X, y, epochs=10, batch_size=16, validation_split=0.2)

model.save("fingerprint_model.keras")
print("✔ Model ve eğitim istatistikleri kaydedildi.")