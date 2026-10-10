<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="isRegister" value="${requestScope.authRegister == true}"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><c:choose><c:when test="${isRegister}">Create account</c:when><c:otherwise>Welcome back</c:otherwise></c:choose> · ClinicManager</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <script>
        tailwind.config = { theme: { extend: { colors: { ink: '#102a43', ocean: '#1677a8', mint: '#e9f7f5', line: '#d8e4ec' }, boxShadow: { soft: '0 24px 70px rgba(16,42,67,.10)' } } } };
    </script>
</head>
<body class="min-h-screen bg-[#f7fafc] text-ink antialiased">
<main class="grid min-h-screen lg:grid-cols-[.9fr_1.1fr]">
    <section class="relative hidden overflow-hidden bg-[#0f3d56] px-10 py-12 text-white lg:flex lg:flex-col lg:justify-between xl:px-20">
        <div class="absolute -right-28 -top-24 h-80 w-80 rounded-full border-[36px] border-white/10"></div>
        <div class="absolute -bottom-32 -left-20 h-96 w-96 rounded-full border-[48px] border-[#43c6b7]/20"></div>
        <div class="relative">
            <a href="${ctx}/" class="flex items-center gap-3 text-lg font-semibold tracking-tight">
                <span class="grid h-10 w-10 place-items-center rounded-xl bg-[#43c6b7] text-[#0f3d56]">+</span>
                ClinicManager
            </a>
        </div>
        <div class="relative max-w-md">
            <p class="mb-5 text-sm font-semibold uppercase tracking-[.22em] text-[#8fe4d8]">Care, connected</p>
            <h1 class="text-4xl font-semibold leading-tight xl:text-5xl">A calmer way to manage every consultation.</h1>
            <p class="mt-6 max-w-sm text-base leading-7 text-white/70">Keep your appointments, care team, and clinical history together in one secure space.</p>
        </div>
        <p class="relative text-sm text-white/50">Patient portal · Secure access</p>
    </section>

    <section class="flex items-center justify-center px-5 py-10 sm:px-8">
        <div class="w-full max-w-xl">
            <div class="mb-10 flex items-center gap-3 lg:hidden">
                <span class="grid h-10 w-10 place-items-center rounded-xl bg-[#0f3d56] text-xl font-semibold text-[#8fe4d8]">+</span>
                <span class="text-lg font-semibold text-ink">ClinicManager</span>
            </div>
            <div class="mb-8">
                <p class="mb-3 text-sm font-semibold uppercase tracking-[.18em] text-ocean">Patient portal</p>
                <h2 class="text-3xl font-semibold tracking-tight text-ink"><c:choose><c:when test="${isRegister}">Create your account</c:when><c:otherwise>Welcome back</c:otherwise></c:choose></h2>
                <p class="mt-2 text-sm leading-6 text-slate-500"><c:choose><c:when test="${isRegister}">Start managing your care in a few simple steps.</c:when><c:otherwise>Sign in to view your appointments and care history.</c:otherwise></c:choose></p>
            </div>

            <div class="mb-8 grid grid-cols-2 rounded-xl border border-line bg-white p-1 shadow-sm">
                <a href="${ctx}/auth/login" class="rounded-lg px-4 py-3 text-center text-sm font-semibold transition <c:choose><c:when test="${isRegister}">text-slate-500 hover:bg-slate-50</c:when><c:otherwise>bg-ink text-white shadow-sm</c:otherwise></c:choose>">Sign in</a>
                <a href="${ctx}/auth/register" class="rounded-lg px-4 py-3 text-center text-sm font-semibold transition <c:choose><c:when test="${isRegister}">bg-ink text-white shadow-sm</c:when><c:otherwise>text-slate-500 hover:bg-slate-50</c:otherwise></c:choose>">Create account</a>
            </div>

            <c:if test="${not empty formError}">
                <div role="alert" class="mb-5 rounded-xl border border-red-200 bg-red-50 px-4 py-3 text-sm text-red-700">${formError}</div>
            </c:if>
            <c:if test="${not empty errors}">
                <div role="alert" class="mb-5 rounded-xl border border-red-200 bg-red-50 px-4 py-3 text-sm text-red-700">
                    <ul class="list-disc space-y-1 pl-5"><c:forEach items="${errors}" var="error"><li>${error}</li></c:forEach></ul>
                </div>
            </c:if>
            <c:if test="${param.registered == 'true'}">
                <div role="status" class="mb-5 rounded-xl border border-emerald-200 bg-emerald-50 px-4 py-3 text-sm text-emerald-700">Your account is ready. Sign in to continue.</div>
            </c:if>

            <c:choose>
                <c:when test="${isRegister}">
                    <form method="post" action="${ctx}/auth/register" class="space-y-5">
                        <div class="grid gap-5 sm:grid-cols-2">
                            <div><label for="firstName" class="mb-2 block text-sm font-medium text-slate-700">First name</label><input id="firstName" name="firstName" type="text" autocomplete="given-name" required class="w-full rounded-xl border border-line bg-white px-4 py-3 text-sm outline-none transition placeholder:text-slate-400 focus:border-ocean focus:ring-4 focus:ring-sky-100" placeholder="Alex"></div>
                            <div><label for="lastName" class="mb-2 block text-sm font-medium text-slate-700">Last name</label><input id="lastName" name="lastName" type="text" autocomplete="family-name" required class="w-full rounded-xl border border-line bg-white px-4 py-3 text-sm outline-none transition placeholder:text-slate-400 focus:border-ocean focus:ring-4 focus:ring-sky-100" placeholder="Morgan"></div>
                        </div>
                        <div><label for="email" class="mb-2 block text-sm font-medium text-slate-700">Email address</label><input id="email" name="email" type="email" autocomplete="email" required class="w-full rounded-xl border border-line bg-white px-4 py-3 text-sm outline-none transition placeholder:text-slate-400 focus:border-ocean focus:ring-4 focus:ring-sky-100" placeholder="you@example.com"></div>
                        <div><label for="phone" class="mb-2 block text-sm font-medium text-slate-700">Phone number</label><input id="phone" name="phone" type="tel" autocomplete="tel" inputmode="tel" required class="w-full rounded-xl border border-line bg-white px-4 py-3 text-sm outline-none transition placeholder:text-slate-400 focus:border-ocean focus:ring-4 focus:ring-sky-100" placeholder="+212 600 000 000"></div>
                        <div><label for="password" class="mb-2 block text-sm font-medium text-slate-700">Password</label><input id="password" name="password" type="password" autocomplete="new-password" minlength="6" required class="w-full rounded-xl border border-line bg-white px-4 py-3 text-sm outline-none transition placeholder:text-slate-400 focus:border-ocean focus:ring-4 focus:ring-sky-100" placeholder="At least 6 characters"><p class="mt-2 text-xs text-slate-500">Use at least 6 characters.</p></div>
                        <button type="submit" class="w-full rounded-xl bg-ink px-4 py-3.5 text-sm font-semibold text-white shadow-sm transition hover:bg-[#183d5b] focus:outline-none focus:ring-4 focus:ring-sky-100">Create account</button>
                    </form>
                </c:when>
                <c:otherwise>
                    <form method="post" action="${ctx}/auth/login" class="space-y-5">
                        <div><label for="email" class="mb-2 block text-sm font-medium text-slate-700">Email address</label><input id="email" name="email" type="email" autocomplete="email" required class="w-full rounded-xl border border-line bg-white px-4 py-3 text-sm outline-none transition placeholder:text-slate-400 focus:border-ocean focus:ring-4 focus:ring-sky-100" placeholder="you@example.com"></div>
                        <div><div class="mb-2 flex items-center justify-between"><label for="password" class="block text-sm font-medium text-slate-700">Password</label><span class="text-xs text-slate-400">Minimum 6 characters</span></div><input id="password" name="password" type="password" autocomplete="current-password" minlength="6" required class="w-full rounded-xl border border-line bg-white px-4 py-3 text-sm outline-none transition placeholder:text-slate-400 focus:border-ocean focus:ring-4 focus:ring-sky-100" placeholder="Enter your password"></div>
                        <button type="submit" class="w-full rounded-xl bg-ink px-4 py-3.5 text-sm font-semibold text-white shadow-sm transition hover:bg-[#183d5b] focus:outline-none focus:ring-4 focus:ring-sky-100">Sign in</button>
                    </form>
                </c:otherwise>
            </c:choose>
            <p class="mt-8 text-center text-xs leading-5 text-slate-400">By continuing, you agree to use ClinicManager responsibly and keep your account information private.</p>
        </div>
    </section>
</main>
</body>
</html>