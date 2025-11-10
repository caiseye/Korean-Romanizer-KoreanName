# Korean-Romanizer-KoreanName
Specialised for Korean names, providing a variety of romanized name combinations.

## English
### About
This library focuses on Korean personal names and generates multiple romanization combinations.

### Features
- Accepts Korean names (Hangul) and outputs romanized formats.
- Supports various combination styles (surname first, spaced forms, hyphenated forms).
- Useful for passport applications, resume formatting, and international system compatibility.

### Getting Started
Clone the repository:
git clone https://github.com/caiseye/Korean-Romanizer-KoreanName.git

Example Usage (Java):
Romanizer romanizer = new KoreanNameRomanizer();
String result = romanizer.romanize("박지성");
System.out.println(result);  // e.g., "Park Jisung", "Bak Ji-seong", etc.

Optional Configuration:
- Surname style
- Spacing rules
- Hyphen usage

License:
MIT License

---
## 한국어

### 개요
한글 이름을 입력하면 다양한 로마자 표기 조합을 생성해주는 라이브러리입니다.

### 주요 기능
- 한국어 이름(한글) → 로마자 표기 변환
- 다양한 표기 방식 지원 (성 먼저 / 띄어쓰기 / 하이픈 적용 등)
- 여권, 이력서, 해외 시스템 대응에 활용 가능

### 사용 시작
저장소 복제:
git clone https://github.com/caiseye/Korean-Romanizer-KoreanName.git

API 사용 예시 (Java):
Romanizer romanizer = new KoreanNameRomanizer();
String result = romanizer.romanize("박지성");
System.out.println(result);  // 예: "Park Jisung", "Bak Ji-seong" 등

스타일 옵션:
- 성씨 표기 방식
- 띄어쓰기 규칙
- 하이픈 여부

라이선스:
MIT License

