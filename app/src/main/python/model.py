
import pytesseract
import cv2
import numpy as np

pytesseract.pytesseract.tesseract_cmd = 'C:\Program Files\Tesseract-OCR\\tesseract.exe'

roi = [[(42, 272), (147, 329), 'text', 'fistname'],
       [(47, 384), (164, 432), 'text', 'lastname'],
       [(339, 424), (359, 427), 'text', 'birthday'],
       [(89, 542), (239, 589), 'text', 'city'],
       [(927, 629), (1097, 687), 'text', 'cin']]

imgQ = cv2.imread("images/card.jpg")

h, w, c = imgQ.shape

#imgQ = cv2.resize(imgQ, (w//3, h//3) )

#cv2.imshow("output",imgQ)

orb = cv2.ORB_create(1000)

kp1, des1 = orb.detectAndCompute(imgQ, None)

imgShow = imgQ.copy()
imgMask = np.zeros_like(imgShow)
myData = []
for x,r in enumerate(roi):
    # For displaying the rois
    cv2.rectangle(imgMask, (r[0][0], r[0][1]), (r[1][0], r[1][1]), (0,
                                                                    255,0), cv2.FILLED)
    imgShow = cv2.addWeighted(imgShow, 0.99, imgMask, 0.1, 0)

    imgCrop= imgQ[r[0][1]:r[1][1],r[0][0]:r[1][0]]

    if r[2] == 'text':
        print('{}: {}'.format(r[3],pytesseract.image_to_string(imgCrop)))
        myData.append(pytesseract.image_to_string(imgCrop))




