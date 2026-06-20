from ultralytics import YOLO

# 1. Modeli yükle (Segmentasyon için nano model)
model = YOLO("yolov8n-seg.pt")

# 2. 80 Verilik Eğitimi Başlat
results = model.train(
    data="dataset_yolo/data.yaml", 
    epochs=100,                     
    imgsz=160,                      
    batch=16,                       # Veri arttığı için batch boyutu 16 yapıldı
    device="cpu",                   
    project="runs/segment",         
    name="parmak_izi_yolo_80",      # 80 verilik özel eğitim adı
    verbose=True                    
)

print("✔ YOLO Eğitimi tamamlandı. Model 'runs/segment/parmak_izi_yolo_80/weights/best.pt' konumuna kaydedildi.")