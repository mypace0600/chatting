let index = {
    init : function () {
        $("#emailDuplicate").on("click", () => {
            this.checkEmailDuplicate();
        });
        $("#emailVerificationCodeButton").on("click", () => {
            this.sendVerificationCode();
        });
        $("#verifyEmailCodeButton").on("click", () => {
            this.verifyEmailCode();
        });
        $("#checkNicknameDuplicateButton").on("click", () => {
            this.checkNicknameDuplicate();
        });
        $("#confirmPassword").on("input", () => {
            this.checkPasswordMatch();
        });
    },

    checkEmailDuplicate : function (){
        let email = $("#email").val();
        console.log(email);
        let data = {
            email : email
        };

        $.ajax({
            type:"POST",
            url:"/user/check-email",
            data:JSON.stringify(data), // javascript object인 data를 json 형식으로 변환해서 java가 인식할 수 있도록 준비함
            contentType:"application/json; charset=utf-8", // http body 데이터가 어떤 타입인지(MIME)
            dataType:"json" // 요청에 대한 응답이 왔을 때 기본적으로 문자열(생긴게 json이라면)=> javascript object로 변경해줌
        }).done(function (resp){
            if(resp.status===500){
                alert("이메일 조회 실패");
            } else if(resp.status===409) {
                alert("이메일 중복 되었습니다.");
            } else {
                $("#emailDuplicateCheck").val(true);
                alert("이메일 중복이 없습니다.");
            }
        }).fail(function (error){
            alert(JSON.stringify(error));
        });
    },
    sendVerificationCode: function () {
            let email = $("#email").val();
            let data = { email: email };

            $.ajax({
                type: "POST",
                url: "/mail/send-verify-code",
                data: JSON.stringify(data),
                contentType: "application/json; charset=utf-8",
                dataType: "json"
            }).done(function (resp) {
                if (resp.status === 200) {
                    alert("인증번호가 발송되었습니다.");
                } else {
                    alert("인증번호 발송 실패");
                }
            }).fail(function (error) {
                alert(JSON.stringify(error));
            });
        },

        verifyEmailCode: function () {
            let code = $("#emailVerificationCode").val();
            let data = { code: code };

            $.ajax({
                type: "POST",
                url: "/mail/check-verify-code",
                data: JSON.stringify(data),
                contentType: "application/json; charset=utf-8",
                dataType: "json"
            }).done(function (resp) {
                if (resp.status === 200) {
                    $("#emailVerifyCheck").val(true);
                    alert("인증번호가 확인되었습니다.");
                } else {
                    $("#emailVerifyCheck").val(false);
                    alert("인증번호가 올바르지 않습니다.");
                }
            }).fail(function (error) {
                alert(JSON.stringify(error));
                $("#emailVerifyCheck").val(false);
            });
        },

        checkPasswordMatch: function () {
            let password = $("#password").val();
            let confirmPassword = $("#confirmPassword").val();

            if (password === confirmPassword) {
                $("#confirmPasswordCheck").val(true);
            } else {
                $("#confirmPasswordCheck").val(false);
            }
        },

        checkNicknameDuplicate: function () {
            let nickname = $("#nickname").val();
            let data = { nickname: nickname };

            $.ajax({
                type: "POST",
                url: "/user/check-nickname",
                data: JSON.stringify(data),
                contentType: "application/json; charset=utf-8",
                dataType: "json"
            }).done(function (resp) {
                if (resp.status === 500) {
                    alert("닉네임 조회 실패");
                    $("#nicknameDuplicateCheck").val(false);
                } else if (resp.status === 409) {
                    alert("닉네임이 중복 되었습니다.");
                    $("#nicknameDuplicateCheck").val(false);
                } else {
                    $("#nicknameDuplicateCheck").val(true);
                    alert("닉네임 중복이 없습니다.");
                }
            }).fail(function (error) {
                alert(JSON.stringify(error));
                $("#nicknameDuplicateCheck").val(false);
            });
        }

}

index.init();