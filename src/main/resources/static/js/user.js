let index = {
    init : function () {
        $("#emailDuplicate").on("click",()=>{
            this.checkEmailDuplicate();
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
                alert("이메일 중복이 없습니다.");
            }
        }).fail(function (error){
            alert(JSON.stringify(error));
        });
    }

}

index.init();