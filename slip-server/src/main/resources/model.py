import cv2
import dill  # pickle 대신 dill 사용
import random

def main():
    # 저장된 모델 파일을 불러오기
    file_name = 'output_model.pkl'
    with open(file_name, 'rb') as file:
        loaded_model = dill.load(file)

    # 동영상 파일 경로
    video_path = '../../user_video/video_1701393655848.mp4'

    # 동영상 파일 불러오기
    video_capture = cv2.VideoCapture(video_path)

    if not video_capture.isOpened():
        print("동영상을 열 수 없습니다.")
        exit()

    # 연속으로 나온 1의 개수를 세는 변수
    count_ones = 0

    while True:
        ret, frame = video_capture.read()

        if not ret:
            break

        output = loaded_model.get_output()

        # 1이 연속으로 5번 나오면 '배회' 출력
        if output == 1:
            count_ones += 1
            if count_ones == 5:
                print("배회")
                count_ones = 0
        else:
            count_ones = 0

    video_capture.release()
    cv2.destroyAllWindows()


if __name__ == "__main__":
    main()
