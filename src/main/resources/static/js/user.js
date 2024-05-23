let index = {
    init: function () {
        $("#emailDuplicateCheckBtn").on("click", () => {
            this.emailDuplicateCheck();
        });
        $("#emailVerifyCodeSendBtn").on("click", () => {
            this.emailVerifyCodeSend();
        });
        $("#verifyEmailCheckBtn").on("click", () => {
            this.verifyEmailCheck();
        });
        $("#nicknameDuplicateCheckBtn").on("click", () => {
            this.nicknameDuplicateCheck();
        });
        $("#joinBtn").on("click", () => {
            this.join();
        });
    },

    emailDuplicateCheck: function () {
        let email = $("#email").val();
        console.log(email);
        let data = {
            email: email
        };

        $.ajax({
            type: "POST",
            url: "/user/check-email",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function (resp) {
            if (resp.status === 500) {
                alert("이메일 조회 실패");
            } else if (resp.status === 409) {
                alert("이메일 중복 되었습니다.");
            } else {
                $("#emailDuplicateCheck").val(true);
                alert("이메일 중복이 없습니다.");
                $("#emailVerifyCodeSendBtn").show();
            }
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    emailVerifyCodeSend: function () {
        let email = $("#email").val();
        let data = { emailAddress: email };

        $.ajax({
            type: "POST",
            url: "/mail/send-verify-code",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function (resp) {
            if (resp.status === 200) {
                alert("인증번호가 발송되었습니다.");
                $("#emailVerificationGroup").show();
            } else {
                alert("인증번호 발송 실패");
            }
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    verifyEmailCheck: function () {
        let code = $("#emailVerificationCode").val();
        let email = $("#email").val();
        let data = {
            verifyCode: code,
            emailAddress: email
        };

        $.ajax({
            type: "POST",
            url: "/mail/check-verify-code",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function (resp) {
            if (resp.status === 200 && resp.data) {
                $("#emailVerifyCheck").val(true);
                alert("인증번호가 확인되었습니다.");
            } else {
                alert("인증번호가 올바르지 않습니다.");
            }
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    nicknameDuplicateCheck: function () {
        let nickName = $("#nickName").val();
        let data = { nickName: nickName };

        $.ajax({
            type: "POST",
            url: "/user/check-nickname",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function (resp) {
            if (resp.status === 500) {
                alert("닉네임 조회 실패");
            } else if (resp.status === 409) {
                alert("닉네임이 중복 되었습니다.");
            } else {
                $("#nicknameDuplicateCheck").val(true);
                alert("닉네임 중복이 없습니다.");
            }
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    join: function () {
        let emailDuplicateCheck = $("#emailDuplicateCheck").val() === 'true';
        let emailVerifyCheck = $("#emailVerifyCheck").val() === 'true';
        let nicknameDuplicateCheck = $("#nicknameDuplicateCheck").val() === 'true';

        if (emailDuplicateCheck && emailVerifyCheck && nicknameDuplicateCheck) {
            let email = $("#email").val();
            let password = $("#password").val();
            let nickName = $("#nickName").val();
            let data = {
                email: email,
                password: password,
                nickName: nickName
            };

            $.ajax({
                type: "POST",
                url: "/auth/joinProc",
                data: JSON.stringify(data),
                contentType: "application/json; charset=utf-8",
                dataType: "json",
            }).done(function (resp) {
                if (resp.success) {
                    alert("회원가입 성공");
                    window.location.href = '/';
                } else {
                    alert("회원가입 실패: " + resp.message);
                }
            }).fail(function (error) {
                let responseJSON = error.responseJSON;
                if (responseJSON && responseJSON.message) {
                    alert("회원가입 실패: " + responseJSON.message);
                } else {
                    alert("회원가입 실패: 서버 오류");
                }
            });
        } else {
            alert('모든 항목을 올바르게 입력해주세요.');
        }
    }
};

index.init();
