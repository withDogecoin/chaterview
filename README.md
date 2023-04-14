# Chaterview

## Domain

- Member
- Quiz
- MemberQuizAnswer
- Subject
- Job
- Prompt (Caching)

## ERD

![erdiagram](https://user-images.githubusercontent.com/47518272/231928286-7ccecba5-9810-4696-b003-238b51f534ae.png)

## View

### Main

![main-app](https://user-images.githubusercontent.com/47518272/231760338-332831c2-37b0-4033-b762-6a78733571f5.png)

__Policy__

- Only members can use the service.
- Click interviews to solve 10 randomly selected questions.
- The Quiz in interviews depending on whether member set up a job on Mypage.
  - e.g Job: Backend Engineer, FrontEnd Engineer, Cloud Engineer ... and so on.
- Interviews can be conducted once a day. If you want to proceed more than once, or if you want to review it, you have to proceed with the __payment__.
  - The payment is subscription-type, and it's 4,900 won per month.
- If you click Review, you can review the problem you got wrong.
  - The maximum number of items shown at a time is 20, and if there are 100 wrong questions, you should divide them into 5 times.

__MyPage__

- Member can see the questions I solved and the questions I got wrong by item.
  - The quiz is categorized. (e.g redis, kafka, spring ... )

### Interviews and Reviews

![interviews](https://user-images.githubusercontent.com/47518272/231763952-a2ab9c4d-d69e-47dd-aa8b-0cbf9ed0da03.png)

__Policy__

- The screen consists of three areas.
  1. Areas where the quiz is provided.
  2. Area to enter answers
  3. The area where you can see the correct answer and AI's feedback
  
### End of interview screen

![end](https://user-images.githubusercontent.com/47518272/231765541-a54d61c7-af6d-4528-93ec-607ab4f8abef.png)

__Policy__

- If pass the interviews then you can show "Congratulations on passing the job interview" otherwise "Thank you for participating in the interview"
- The button provides Home and Mypage

## Quiz Recommended Algorithms

Quiz recommendation is Random based by default. If you solve more than 100 questions, I recommend a question similar to the wrong one.

## User Answer Processing Algorithms

The answer entered by the user and the prompt managed by the DB are combined and delivered to Chat AI. Since the prompt changes very little, it uses caching to reduce the I/O load.
