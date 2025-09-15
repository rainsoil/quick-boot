@echo off
echo Testing backend service...
echo.

echo 1. Testing login endpoint:
curl -X POST "http://localhost:12000/login" -H "Content-Type: application/x-www-form-urlencoded" -d "username=admin&password=admin123"
echo.
echo.

echo 2. Testing logout endpoint:
curl -X POST "http://localhost:12000/logout"
echo.
echo.

echo 3. Testing captcha endpoint:
curl -X GET "http://localhost:12000/captchaImage"
echo.
echo.

echo 4. Testing getInfo endpoint:
curl -X GET "http://localhost:12000/getInfo"
echo.
echo.

echo Test completed.
pause
