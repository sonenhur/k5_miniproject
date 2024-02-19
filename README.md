1. **회원가입 (MemberController, MemberService, MemberRepository)**:
   - 사용자가 회원가입을 요청하면 MemberController의 join() 메서드가 호출됩니다.
   - 회원 정보는 MemberService의 join() 메서드를 통해 처리됩니다.
   - MemberService에서는 입력된 회원 정보를 검증하고, 새로운 Member 엔티티를 생성하여 MemberRepository를 통해 저장합니다.

2. **로그인 (MemberController, MemberService, JwtProvider)**:
   - 사용자가 로그인을 시도하면 MemberController의 login() 메서드가 호출됩니다.
   - MemberService의 login() 메서드는 사용자의 이메일과 비밀번호를 확인하고, 유효한 경우 JWT 토큰을 생성하여 반환합니다.

3. **회원 정보 조회 (MemberController, MemberService)**:
   - 인증된 사용자가 회원 정보를 조회하면 MemberController의 getInfo() 메서드가 호출됩니다.
   - MemberService의 getInfo() 메서드는 사용자의 이메일을 기반으로 회원 정보를 가져와서 반환합니다.

4. **리뷰 작성 (ReviewController, ReviewService)**:
   - 사용자가 리뷰를 작성하면 ReviewController의 save() 메서드가 호출됩니다.
   - ReviewService의 save() 메서드는 해당 사용자의 회원 정보를 확인하고, 입력된 리뷰를 저장합니다.

5. **리뷰 조회 (ReviewController, ReviewService)**:
   - 사용자가 특정 리뷰를 조회하면 ReviewController의 getReview() 메서드가 호출됩니다.
   - ReviewService의 getReview() 메서드는 요청된 리뷰를 찾아서 해당 리뷰의 정보를 반환합니다.

6. **리뷰 수정 (ReviewController, ReviewService)**:
   - 사용자가 리뷰를 수정하면 ReviewController의 edit() 메서드가 호출됩니다.
   - ReviewService의 edit() 메서드는 요청된 리뷰를 찾아서 해당 리뷰의 내용을 수정합니다.

7. **리뷰 삭제 (ReviewController, ReviewService)**:
   - 사용자가 리뷰를 삭제하면 ReviewController의 delete() 메서드가 호출됩니다.
   - ReviewService의 delete() 메서드는 요청된 리뷰를 찾아서 해당 리뷰를 삭제합니다.
