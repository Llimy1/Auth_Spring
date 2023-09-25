document.addEventListener("DOMContentLoaded", function() {
    // 등록 버튼을 클릭할 때 이벤트 처리
    var sign = document.getElementById("sign");
    sign.addEventListener("click", function() {
        var email = document.querySelector('input[name=email]').value;
        // 닉네임 값 가져오기
        var nickname = document.querySelector('input[name="nickname"]').value;

        // 폰 번호 값 가져오기
        var phoneNumber = document.querySelector('input[name="phoneNumber"]').value;

        // 성별 값 가져오기
        var gender = document.querySelector('input[name="gender"]').value;

        // 소개 값 가져오기
        var introduce = document.querySelector('input[name="introduce"]').value;

        // 메인 주소 값 가져오기
        var mainAddress = document.querySelector('input[name="mainAddress"]').value;

        // 서브 주소 값 가져오기
        var detailAddress = document.querySelector('input[name="detailAddress"]').value;

        // 회원 가입 양식에서 입력된 정보 가져오기
        const userData = {
            email: email,
            nickname: nickname,
            phoneNumber: phoneNumber,
            gender: gender,
            introduce: introduce,
            mainAddress: mainAddress,
            detailAddress: detailAddress
        };

        // API 요청 보내기
        fetch('/api/v1/signup/oauth2', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(userData)
        })
            .then(function(response) {
                if (response.status) {
                    // 회원 가입 성공 시 처리
                    console.log("회원 가입이 성공적으로 완료되었습니다.");
                    // 여기에서 원하는 작업을 수행하세요. 예를 들어 로그인 페이지로 이동 등.
                    window.location.href = "index.html";
                } else {
                    // 회원 가입 실패 시 처리
                    console.error("회원 가입에 실패했습니다.");
                }
            })
            .catch(function(error) {
                console.error("API 요청 중 오류 발생:", error);
            });
    });
});
